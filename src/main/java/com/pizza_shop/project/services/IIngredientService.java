package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Ingredient;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IIngredientService {
    Ingredient createIngredient(Ingredient ingredient, MultipartFile image);
    List<Ingredient> getAllIngredients();
    Ingredient updateIngredient(Ingredient ingredient);
    void deleteIngredient(int id);
    Ingredient getIngredient(int id);

    byte[] getImageByPath(String path);
}
