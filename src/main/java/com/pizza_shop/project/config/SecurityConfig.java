package com.pizza_shop.project.config;

import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailService;

   @Autowired
   private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("dumb").password("1111111111")
                .roles("USER").and().and().userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/ingredient").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/size/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/user").anonymous()
                .antMatchers(HttpMethod.GET, "/user").authenticated()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/stripe/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/user/authenticate/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/pizza").permitAll()
                .antMatchers(HttpMethod.POST, "/pizza").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/size/**").permitAll()
                .antMatchers(HttpMethod.GET,"/ingredient").permitAll()
                .antMatchers(HttpMethod.GET,"/ingredient/**").anonymous()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
