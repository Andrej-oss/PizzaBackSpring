package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Cart;

import java.util.List;

public interface ICartService {

    List<Cart> getAllCartElements();
    Cart getOneCartElement(int id);
    List<Cart> createCartElement(Cart cart, int userId);
    Cart updateCartElement(int id);
    void deleteCartElement(int id);

    List<Cart> getAllPizzasByUserId(int userId);

    void addElementInCart(int cartId, int price);

    void removePizzaInCart(int cartId, int pricePizza);
}
