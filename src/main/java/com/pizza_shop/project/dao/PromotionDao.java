package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionDao extends JpaRepository<Promotion, Integer> {
    Promotion getPromotionByPath(String path);
}
