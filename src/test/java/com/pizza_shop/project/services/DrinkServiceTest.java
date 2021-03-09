package com.pizza_shop.project.services;

import com.pizza_shop.project.config.StorageDrinkConfig;
import com.pizza_shop.project.dao.DrinkDao;
import com.pizza_shop.project.entity.Drink;
import com.pizza_shop.project.services.impl.DrinkService;
import io.jsonwebtoken.lang.Assert;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DrinkServiceTest {

    @Mock
    private DrinkDao drinkDao;
    @Mock
    private StorageDrinkConfig storageDrinkConfig;
    @InjectMocks
    private DrinkService drinkService;

    private List<Drink> drinks;
    private  Drink drink1;
    private  Drink drink2;

    @BeforeEach
    public  void init(){
        drinks = new ArrayList<Drink>();
        drink1 = new Drink(1, "Coca cola", new byte[]{34, 0,-1,111,43}, 0.5, 3, 323, "/cola0.5");
        drink2 = new Drink(2, "Bon Aqua", new byte[]{3, -121,-1,89,10}, 0.5, 2, 3, "/bonaqua0.5");
        drinks.add(drink1);
        drinks.add(drink2);
    }
    @Test
    public void givenNothingWhenGettingAllDrinksReturnAllDrinks(){
        Mockito.when(drinkDao.findAll()).thenReturn(drinks);

        final List<Drink> actualDrinks = drinkService.getAllDrinks();
        Assertions.assertEquals(actualDrinks.get(0).getName(), drinks.get(0).getName());
    }
    @Test
    public void givenDrinkIdWhenDeletingDessertReturnAllDrinks(){
        Mockito.when(drinkDao.getOne(ArgumentMatchers.anyInt())).thenReturn(drink1);
        Mockito.doNothing().when(drinkDao).delete(drink1);
        drinks.remove(drink1);
        Mockito.when(drinkDao.findAll()).thenReturn(drinks);
        final List<Drink> actualDrinks = drinkService.deleteDrink(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualDrinks.get(0).getName(), this.drinks.get(0).getName());
    }
    @Test
    public void givenPathDrinkImageWhenGettingImageByPathReturnImage(){
        Mockito.when(drinkDao.getDrinkByPath(ArgumentMatchers.anyString())).thenReturn(drink1);

        final byte[] imageByPath = drinkService.getImageByPath(ArgumentMatchers.anyString());
        Assertions.assertEquals(imageByPath, drink1.getData());
    }
}
