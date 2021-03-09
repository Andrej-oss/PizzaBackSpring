package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.PizzaDao;
import com.pizza_shop.project.dao.RatingDao;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.Rating;
import com.pizza_shop.project.services.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private PizzaDao pizzaDao;

    @Override
    public Rating saveRating(Rating rating, int pizzaId) {
        final Pizza pizza = pizzaDao.getOne(pizzaId);
        if (pizza != null){
            rating.setPizza(pizza);
        }
        final List<Rating> allRatingsByPizzaId = getAllRatingsByPizzaId(pizzaId);
        for (Rating rating1 : allRatingsByPizzaId) {
            if (rating1.getUserId() != rating.getUserId()){
                 ratingDao.save(rating);
            }
        }
        if (allRatingsByPizzaId.size() == 0) ratingDao.save(rating);
        return null;
    }

    @Override
    public List<Rating> getAllRatings() {
        return null;
    }

    @Override
    public Rating getRating(int id) {
        return null;
    }

    @Override
    public boolean updateRating(int id) {
        return false;
    }

    @Override
    public boolean deleteRating(int id) {
        return false;
    }

    @Override
    public List<Rating> getAllRatingsByPizzaId(int pizzaId) {
        return ratingDao.getAllRatingsByPizzaId(pizzaId);
    }
}
