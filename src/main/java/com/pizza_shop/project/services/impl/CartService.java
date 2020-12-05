package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.CartDao;
import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.entity.Cart;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Cart> getAllCartElements() {
        return cartDao.findAll();
    }

    @Override
    public Cart getOneCartElement(int id) {
        return null;
    }

    @Override
    public List<Cart> createCartElement(Cart cart, int userId) {
        final User one = userDao.getOne(userId);
        if (one != null){
            cart.setUser(one);
        }
        cartDao.save(cart);
        return cartDao.findAllCartsByUserId(userId);
    }

    @Override
    public Cart updateCartElement(int id) {
        return null;
    }

    @Override
    public void deleteCartElement(int id) {
    }

    @Override
    public List<Cart> getAllPizzasByUserId(int userId) {
        return cartDao.findAllCartsByUserId(userId);
    }
}
