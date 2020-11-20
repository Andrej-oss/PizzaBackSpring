package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Pizza;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPizzaService {
    Pizza createPizza(Pizza pizza, MultipartFile file);
    Pizza updatePizza(int id);
    List<Pizza> getAllPizzas();
    void deletePizza(int id);
    Pizza getPizza(int id);

    byte[] getPizzaImage(String path);
}
