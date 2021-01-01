package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Pizza;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPizzaService {
    Pizza createPizza(Pizza pizza, MultipartFile file);
    List<Pizza> updatePizza(int id, Pizza pizza, MultipartFile file);
    List<Pizza> getAllPizzas();
    List<Pizza> deletePizza(int id);
    Pizza getPizza(int id);

    byte[] getPizzaImage(String path);
}
