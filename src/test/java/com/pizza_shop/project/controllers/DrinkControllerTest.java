package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Dessert;
import com.pizza_shop.project.entity.Drink;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.DrinkService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DrinkController.class)
@Import(SecurityConfig.class)
public class DrinkControllerTest {
    @MockBean
    private DrinkService drinkService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;

    private  List<Drink> drinks;
    private  Drink drink1;
    private  Drink drink2;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public  void init(){
        drinks = new ArrayList<Drink>();
       drink1 = new Drink(1, "Coca cola", new byte[]{34, 0,-1,111,43}, 0.5, 3, 323, "/cola0.5");
        drink2 = new Drink(2, "Bon Aqua", new byte[]{3, -121,-1,89,10}, 0.5, 2, 3, "/bonaqua0.5");
        drinks.add(drink1);
        drinks.add(drink2);
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenValidDrinkBodyAndImageFileWhenInsertingDrinkReturnAllDrinks() throws Exception {
        Drink drink3 = new Drink(1, "Shweps", new byte[]{34, 0,-1,111,43}, 0.5, 3, 323, "/cola0.5");
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "WX20180207-134704@2x.png",
                "image/png",
                "sweps.jpg".getBytes());
        drinks.add(drink3);
        BDDMockito.when(drinkService.saveDrink(ArgumentMatchers.any(Drink.class), ArgumentMatchers.any(MultipartFile.class))).thenReturn(drinks);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/drink").file(image)
                .flashAttr("drink", drink3))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(drink1, drink2, drink3))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Shweps"));
    }
    @Test
    public void givenNothingWhenGettingAllDrinksReturnAllDrinksAndSuccessfulResponse() throws Exception{
        BDDMockito.when(drinkService.getAllDrinks()).thenReturn(drinks);

        mockMvc.perform(MockMvcRequestBuilders.get("/drink"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(drink1, drink2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
    @Test
    public void givenPathDrinkWhenGettingImageDrinkReturnImageAndSuccessfulResponse() throws Exception{
        String path = "/bonaqua0.5";
        Drink drinkFind = null;
        for (Drink drink: drinks
             ) {
            if (drink.getPath().equals(path)) drinkFind = drink;
        }
        assert drinkFind != null;
        BDDMockito.when(drinkService.getImageByPath(path)).thenReturn(drinkFind.getData());

        mockMvc.perform(MockMvcRequestBuilders.get("/drink{path}", path))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenDrinkIdWhenDeletingDrinkReturnAllDrinks() throws Exception{
        int id = 1;
        Drink drinkFind = null;
        for (Drink drink: drinks
             ) {
            if (drink.getId() == id) drinkFind = drink;
        }
        assert drinkFind != null;
        drinks.remove(drinkFind);
        BDDMockito.when(drinkService.deleteDrink(id)).thenReturn(drinks);

        mockMvc.perform(MockMvcRequestBuilders.delete("/drink/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(drink2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2));
    }
}
