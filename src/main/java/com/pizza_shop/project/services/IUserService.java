package com.pizza_shop.project.services;

import com.pizza_shop.project.dto.PasswordUserDto;
import com.pizza_shop.project.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService extends UserDetailsService {

    User getUser(int id);
    List<User> getAllUsers();
    User updateUser(int id);
    User createUser(User user);
    List<User> deleteUser(int id);

    @Override
    UserDetails loadUserByUsername(String s);

    User getUserByUserName(String name);

    boolean activateUser(String activateCode);

    String sendPasswordUserByEmail(String email);

    User getUserByEmail(String email);

    void changePassword(PasswordUserDto passwordUserDto);

    User getUserByActivationCode(String activationCode);
}
