package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingDao extends JpaRepository<Rating, Integer> {
    List<Rating> getAllRatingsByPizzaId(int pizzaId);

    void getAllRatingsByUserId(int userId);
}
