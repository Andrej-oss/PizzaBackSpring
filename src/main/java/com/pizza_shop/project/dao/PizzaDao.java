package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PizzaDao extends JpaRepository<Pizza, Integer> {

    @Query("select d from Pizza d where d.path = :path")
    Pizza getPizzaImageByPath(String path);
}
