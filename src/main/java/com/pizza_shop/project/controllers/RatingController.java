package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Rating;
import com.pizza_shop.project.services.IRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200/api")
@Slf4j
public class RatingController {

    private IRatingService ratingService;

    @Autowired
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }
    @PostMapping("/rating/{pizzaId}")
    public List<Rating> saveRating(@PathVariable int pizzaId, @RequestBody Rating rating){
        ratingService.saveRating(rating, pizzaId);
        return ratingService.getAllRatingsByPizzaId(pizzaId);
    }
}
