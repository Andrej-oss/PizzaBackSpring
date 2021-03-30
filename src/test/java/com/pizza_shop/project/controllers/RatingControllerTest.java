package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.Rating;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.RatingService;
import com.pizza_shop.project.services.impl.UserService;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
@Import(SecurityConfig.class)
public class RatingControllerTest {

    @MockBean
    private RatingService ratingService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;

    private  List<Rating> ratings;
    private  Rating rating1;
    private Rating rating2;
    private Pizza pizza;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        ratings = new ArrayList<Rating>();
        pizza = new Pizza(1, true, "/scdcsd", "papa", "description", null, 10.00, "1,24,6", 4, null, null, null);
        rating1 = new Rating(1, 1, pizza, 1);
        rating2 = new Rating(2, 2, pizza, 2);
        ratings.add(rating1);
        ratings.add(rating2);
    }

}
