package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Snack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnackDao extends JpaRepository<Snack, Integer> {
    Snack getSnackByPath(String path);
}
