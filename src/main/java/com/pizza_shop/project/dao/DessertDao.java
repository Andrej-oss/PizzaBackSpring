package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Dessert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DessertDao extends JpaRepository<Dessert, Integer> {
    Dessert getDessertByPath(String path);

}
