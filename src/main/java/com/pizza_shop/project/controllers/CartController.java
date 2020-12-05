package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Cart;
import com.pizza_shop.project.services.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class CartController {

    private ICartService cartService;


    @Autowired
    public CartController(ICartService basketService) {
        this.cartService = basketService;
    }

    @PostMapping("/cart/{userId}")
    public List<Cart> createBasket(@RequestBody Cart cart, @PathVariable int userId){
        log.info("Handling /post in createCart with body: " + cart +  "for user_id" + userId);
        return cartService.createCartElement(cart, userId);
    }
    @GetMapping("/cart")
    public List<Cart> getAllBasket(){
        log.info("Handling /get all cart elements");
        return cartService.getAllCartElements();
    }
    @GetMapping("/cart/{userId}")
    public List<Cart> getAllCartPizzaByUserId(@PathVariable int userId){
        log.info("Handling /get all pizzas in the cert by userId " + userId);
        return cartService.getAllPizzasByUserId(userId);
        }
}
