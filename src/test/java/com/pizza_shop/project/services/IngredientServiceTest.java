package com.pizza_shop.project.services;

import com.pizza_shop.project.config.StorageConfig;
import com.pizza_shop.project.dao.IngredientDao;
import com.pizza_shop.project.entity.Ingredient;
import com.pizza_shop.project.services.impl.IngredientService;
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
public class IngredientServiceTest {

    @Mock
    private IngredientDao ingredientDao;

    @Mock
    private StorageConfig storageConfig;
    @InjectMocks
    private IngredientService ingredientService;

    private List<Ingredient> ingredients;
    private  Ingredient ingredient1;
    private  Ingredient ingredient2;

    @BeforeEach
    public  void init(){
        ingredients = new ArrayList<Ingredient>();
        ingredient1 = new Ingredient(1, "pepper", 1, new byte[]{78, 0, 90,-111, 87, 45}, "/pepper");
        ingredient2 = new Ingredient(2, "mozzarella", 2, new byte[]{-18,100, 17,1, 56, 111}, "/mozzarella");
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
    }
    @Test
    public void givenNothingWhenGettingAllIngredientsReturnAllIngredients(){
        Mockito.when(ingredientDao.findAll()).thenReturn(ingredients);

        final List<Ingredient> allIngredients = ingredientService.getAllIngredients();
        Assertions.assertEquals(ingredients.get(0).getName(), allIngredients.get(0).getName());
    }
    @Test
    public void givenIngredientIdWhenDeletingIngredientReturnAllIngredients(){
        Mockito.when(ingredientDao.getOne(ArgumentMatchers.anyInt())).thenReturn(ingredient1);
        Mockito.doNothing().when(ingredientDao).delete(ingredient1);
        ingredients.remove(ingredient1);
        Mockito.when(ingredientDao.findAll()).thenReturn(ingredients);
        final List<Ingredient> actualIngredients = ingredientService.deleteIngredient(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualIngredients.get(0).getId(), 2);
    }
    @Test
    public void givenIngredientIdWhenGettingIngredientReturnIngredient(){
        Mockito.when(ingredientDao.getOne(ArgumentMatchers.anyInt())).thenReturn(ingredient1);

        final Ingredient actualIngredient = ingredientService.getIngredient(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualIngredient.getId(), ingredient1.getId());
    }
    @Test
    public void givenIngredientPathWhenGettingIngredientImageReturnImage(){
        Mockito.when(ingredientDao.getImageByPath(ArgumentMatchers.anyString())).thenReturn(ingredient1);

        final byte[] imageByPath = ingredientService.getImageByPath(ArgumentMatchers.anyString());
        Assertions.assertEquals(imageByPath, ingredient1.getData());
    }
}
