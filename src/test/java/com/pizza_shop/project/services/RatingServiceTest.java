package com.pizza_shop.project.services;

import com.pizza_shop.project.dao.PizzaDao;
import com.pizza_shop.project.dao.RatingDao;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.Rating;
import com.pizza_shop.project.services.impl.RatingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private RatingDao ratingDao;
    @Mock
    private PizzaDao pizzaDao;
    @InjectMocks
    private RatingService ratingService;

    private List<Rating> ratings;
    private  Rating rating1;
    private Rating rating2;
    private Pizza pizza;

    @BeforeEach
    public void init(){
        ratings = new ArrayList<Rating>();
        pizza = new Pizza(1, true, "/scdcsd", "papa", "description", null, 10.00, "1,24,6", 4, null, null, null);
        rating1 = new Rating(1, 1, pizza, 1);
        rating2 = new Rating(2, 2, pizza, 2);
        ratings.add(rating1);
        ratings.add(rating2);
    }
    @Test
    public void givenRatingBodyAndPizzaIdWhenInsertingRatingReturnRating(){
      Rating  rating3 = new Rating(3, 4, pizza, 1);
      Mockito.when(pizzaDao.getOne(rating3.getPizza().getId())).thenReturn(pizza);
        Mockito.when(ratingDao.save(rating3)).thenReturn(rating3);
        final Rating rating = ratingService.saveRating(rating3, pizza.getId());
        Assertions.assertNull(rating);
    }
    @Test
    public void givenPizzaIdWhenGettingAllPizzasRatingReturnAllRatings(){
        Mockito.when(ratingDao.getAllRatingsByPizzaId(ArgumentMatchers.anyInt())).thenReturn(ratings);

        final List<Rating> allRatingsByPizzaId = ratingService.getAllRatingsByPizzaId(ArgumentMatchers.anyInt());
        Assertions.assertEquals(allRatingsByPizzaId.get(0).getId(), ratings.get(0).getId());
    }
}
