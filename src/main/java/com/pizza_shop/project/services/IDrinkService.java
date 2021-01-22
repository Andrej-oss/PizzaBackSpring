package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Drink;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDrinkService {

    Drink getDrink(int id);
    List<Drink> getAllDrinks();
    List<Drink> saveDrink(Drink drink, MultipartFile image);
    List<Drink> updateDrink(int id, Drink drink, MultipartFile image);
    List<Drink> deleteDrink(int id);

    byte[] getImageByPath(String path);
}
