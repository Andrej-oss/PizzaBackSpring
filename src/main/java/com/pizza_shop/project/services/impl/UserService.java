package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.dto.PasswordUserDto;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.exceptions.EmailException;
import com.pizza_shop.project.exceptions.NotExistEmailException;
import com.pizza_shop.project.exceptions.UserNameException;
import com.pizza_shop.project.services.IUserService;
import com.pizza_shop.project.services.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSenderService mailSenderService;

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
            user.setActive(false);
            user.setActivationCode(UUID.randomUUID().toString());
            this.userDao.save(user);
            if (!StringUtils.isEmpty(user.getEmail())){
                String message = String.format(
                        "Hello, %s! \n" +
                        "Welcome to Pizza Shop Please, visit this" +
                                " link to finished registration http://localhost:8080/activate/%s",
                        user.getUsername(),
                        user.getActivationCode());
                mailSenderService.sendMail(user.getEmail(), "Activation Code", message);
            }
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

    @Override
    public User activateUser(String activateCode) {
        final User user = userDao.getUserByActivationCode(activateCode);
        if (user == null){
            return null;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userDao.save(user);
        return user;
    }

    @Override
    public String sendPasswordUserByEmail(String email) {
        final User user = getUserByEmail(email);
        if (user != null){
            user.setActive(false);
            user.setActivationCode(UUID.randomUUID().toString());
            userDao.save(user);
            String message = String.format("Hello, %s!\n" +
                    "Link to changing your password http://localhost:8080/email/activate/%s\n" +
                    "And login is %s.", user.getName(), user.getActivationCode(), user.getUsername());
            mailSenderService.sendMail(user.getEmail(), "Forgotten Password", message);
        }
        if (user == null) throw new NotExistEmailException("Rewrite your email");
        assert user != null;
        return user.getEmail();
    }

    @Override
    public void changePassword(PasswordUserDto passwordUserDto) {
        if(passwordUserDto != null){
            final User user = getUserByUserName(passwordUserDto.getUserName());
            if (user != null && user.getActivationCode() != null
            ){
                user.setActivationCode(null);
                user.setActive(true);
                user.setPassword(passwordEncoder.encode(passwordUserDto.getPassword()));
                userDao.saveAndFlush(user);
            }
        }

    }

    @Override
    public User getUserByActivationCode(String activationCode) {
        return userDao.getUserByActivationCode(activationCode);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
}
