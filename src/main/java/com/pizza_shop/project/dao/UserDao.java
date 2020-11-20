package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserDao extends JpaRepository<User, Integer> {

    @Query(value = "select u from User u where u.email = :email")
    User findUsersByEmail(String email);

    @Query(value = "select u from User u where u.username = :username")
    User findUsersByUsername (String username);

    @Query(value = "select u from User u where u.username = :s")
    UserDetails findByUserName(String s);
}
