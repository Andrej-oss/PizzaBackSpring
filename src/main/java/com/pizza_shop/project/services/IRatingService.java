package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Rating;

import java.util.List;

public interface IRatingService {
    
    Rating saveRating(Rating rating, int pizzaId);
    List<Rating> getAllRatings();
    Rating getRating(int id);
    boolean updateRating(int id);
    boolean deleteRating(int id);

    List<Rating> getAllRatingsByPizzaId(int pizza);
}
