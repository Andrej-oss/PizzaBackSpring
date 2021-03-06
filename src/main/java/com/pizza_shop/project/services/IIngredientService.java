package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Ingredient;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IIngredientService {
    List<Ingredient> createIngredient(Ingredient ingredient, MultipartFile image);
    List<Ingredient> getAllIngredients();
    List<Ingredient> updateIngredient(int id, Ingredient ingredient, MultipartFile file);
    List<Ingredient> deleteIngredient(int id);
    Ingredient getIngredient(int id);

    byte[] getImageByPath(String path);
}
