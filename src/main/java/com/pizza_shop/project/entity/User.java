package com.pizza_shop.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.ToString.Include;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String username;

    @NotBlank
    @JsonIgnore
    private String password;

    private String name;
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String city;
    @NotBlank
    private String address;
    private String postCode;
    @NotBlank
    private String phone;
    private String role;
    private boolean active;
    private String activationCode;
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private List<Comment> comments;
    @ToString.Exclude
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private List<Cart> cartList;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Avatar avatar;
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private List<Purchase> purchases;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public boolean isActive(){
        return active;
    }

}
