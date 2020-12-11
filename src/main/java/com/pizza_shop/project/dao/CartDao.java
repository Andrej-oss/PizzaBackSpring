package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartDao extends JpaRepository<Cart, Integer> {
    List<Cart> findAllCartsByUserId(int userId);
}
