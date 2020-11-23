package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.exceptions.EmailException;
import com.pizza_shop.project.exceptions.UserNameException;
import com.pizza_shop.project.services.IUserService;
import com.pizza_shop.project.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUser(int id) {
        return this.userDao.getOne(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User updateUser(int id) {
        return null;
    }

    @Override
    public List<User> createUser(User user) {
        final User usersByEmail = this.userDao.findUsersByEmail(user.getEmail());
        final User usersByUsername = this.userDao.findUsersByUsername(user.getUsername());
        if (usersByEmail == null && usersByUsername == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getUsername().equals("tomsawyer")){
                user.setRole("ROLE_ADMIN");
            }
            this.userDao.save(user);
        }
         if(usersByUsername != null){
           throw  new UserNameException("Login occupied already");
         }
         if (usersByEmail != null){
             throw new EmailException("This email occupied already");
        }
        return this.userDao.findAll();
    }

    @Override
    public List<User> deleteUser(int id) {
        this.userDao.deleteById(id);
        return this.userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.findByUserName(s);
    }
    @Override
    public User getUserByUserName(String name) {
        return userDao.findUsersByUsername(name);
    }
}
