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
                .antMatchers(HttpMethod.POST, "/pizza").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/dessert").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/snack").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/drink").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/promotion").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/comment").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE,"/ingredient/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/size/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/pizza/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/dessert/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/snack/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/drink/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/promotion/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/ingredient/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/size/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/pizza/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/dessert/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/snack/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/drink/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/promotion/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/user").anonymous()
                .antMatchers(HttpMethod.GET, "/user").authenticated()
                .antMatchers(HttpMethod.GET, "/comment").permitAll()
                .antMatchers(HttpMethod.POST, "/cart**").authenticated()
//                .antMatchers("/user/**").permitAll()
//                .antMatchers("/stripe/**").permitAll()
//               .antMatchers(HttpMethod.GET,"/user/authenticate/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/cart**").authenticated()
                //.antMatchers(HttpMethod.GET, "/cart").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.GET, "/purchase/**").authenticated()
                .antMatchers(HttpMethod.GET,"/pizza").permitAll()
                .antMatchers(HttpMethod.GET,"/size/**").permitAll()
                .antMatchers(HttpMethod.GET,"/ingredient").permitAll()
                .antMatchers(HttpMethod.GET,"/ingredient/**").anonymous()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
