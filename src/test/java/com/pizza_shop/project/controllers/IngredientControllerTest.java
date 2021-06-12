package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Ingredient;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.IngredientService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
@WebMvcTest(IngredientController.class)
@Import(SecurityConfig.class)
public class IngredientControllerTest {
    @MockBean
    private IngredientService ingredientService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;

    private  List<Ingredient> ingredients;
    private  Ingredient ingredient1;
    private  Ingredient ingredient2;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public  void init(){
        ingredients = new ArrayList<Ingredient>();
        ingredient1 = new Ingredient(1, "pepper", 1, new byte[]{78, 0, 90,-111, 87, 45}, "/pepper");
        ingredient2 = new Ingredient(2, "mozzarella", 2, new byte[]{-18,100, 17,1, 56, 111}, "/mozzarella");
     ingredients.add(ingredient1);
     ingredients.add(ingredient2);
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenValidIngredientBodyWhenInsertingIngredientReturnAllIngredients() throws Exception{
        final Ingredient ingredient3 = new Ingredient(1, "garlic", 1, new byte[]{111, 0, -12, -10, 12}, "/garlic");
        ingredients.add(ingredient3);
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "WX20180207-134704@2x.png",
                "image/png",
                "garlic.jpg".getBytes());
        BDDMockito.when(ingredientService.createIngredient(ArgumentMatchers.any(Ingredient.class), ArgumentMatchers.any(MultipartFile.class))).thenReturn(ingredients);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/ingredient")
                .file(image)
                .flashAttr("ingredient", ingredient3))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void givenNothingWhenGettingAllIngredientsReturnAllIngredients() throws Exception{
        BDDMockito.when(ingredientService.getAllIngredients()).thenReturn(ingredients);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ingredient"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(ingredient1, ingredient2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
    @Test
    public void givenIngredientPathWhenGettingImageIngredientReturnImageAndSuccessfulResponseAndImage() throws Exception{
        String path = "/mozzarella";
        Ingredient ingredientFind = null;
        for (Ingredient ingredient: ingredients
             ) {
            if (ingredient.getPath().equals(path)) ingredientFind = ingredient;
        }
        assert ingredientFind != null;
        BDDMockito.when(ingredientService.getImageByPath(path)).thenReturn(ingredientFind.getData());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/ingredient/image{path}", path))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public  void givenIngredientIdWhenDeletingIngredientReturnSuccessfulResponseAndAllIngredients() throws Exception{
        int id = 1;
        Ingredient ingredientFind = null;
        for (Ingredient ingredient: ingredients
             ) {
            if (ingredient.getId() == id) ingredientFind = ingredient;
        }
        assert ingredientFind != null;
        ingredients.remove(ingredientFind);
        BDDMockito.when(ingredientService.deleteIngredient(id)).thenReturn(ingredients);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/ingredient/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(ingredient2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("mozzarella"));
    }
}
