package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkDao extends JpaRepository<Drink, Integer> {
    Drink getDrinkByPath(String path);
}
