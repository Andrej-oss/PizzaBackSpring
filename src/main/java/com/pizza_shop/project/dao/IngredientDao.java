package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredientDao extends JpaRepository<Ingredient, Integer> {

    @Query("select i from Ingredient i where i.path = :path")
     Ingredient getImageByPath(String path);
}
