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
        return cartDao.getOne(id);
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
        final Cart cart = cartDao.getOne(id);
        cartDao.delete(cart);
    }

    @Override
    public List<Cart> getAllPizzasByUserId(int userId) {
        return cartDao.findAllCartsByUserId(userId);
    }

    @Override
    public void addElementInCart(int cartId, int pricePizza) {
         Cart cartElement = cartDao.getOne(cartId);
         int amount = cartElement.getAmount();
         double price = cartElement.getPrice();
        cartElement.setAmount(amount + 1);
        cartElement.setPrice(price + pricePizza);
        cartDao.save(cartElement);
    }

    @Override
    public void removePizzaInCart(int cartId, int pricePizza) {
        final Cart cart = cartDao.getOne(cartId);
         int amount = cart.getAmount();
         double price = cart.getPrice();
         cart.setPrice(price - pricePizza);
         cart.setAmount(amount - 1);
         cartDao.save(cart);
    }
}
