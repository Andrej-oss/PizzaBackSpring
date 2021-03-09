package com.pizza_shop.project.services;

import com.pizza_shop.project.config.StoragePizzaConfig;
import com.pizza_shop.project.dao.PizzaDao;
import com.pizza_shop.project.dto.PizzaDto;
import com.pizza_shop.project.entity.Ingredient;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.services.impl.PizzaService;
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
import org.springframework.data.domain.*;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest {

    @Mock
    private PizzaDao pizzaDao;
    @Mock
    private StoragePizzaConfig storagePizzaConfig;
    @InjectMocks
    private PizzaService pizzaService;

    private List<Pizza> pizzas;
    private Pizza pizza1;
    private Pizza pizza2;

    @BeforeEach
    public void init(){
        pizzas = new ArrayList<Pizza>();
        pizza1 = new Pizza(1, true, "/scdcsd", "papa", "description", null, 10, "1,24,6", 4, null, null, null);
        pizza2 = new Pizza(2, false, "/sfdsfd", "lala", "description", new byte[]{45, 7, 0, 7}, 123, "14,24,6", 4, null, null, null);
        pizzas.add(pizza1);
        pizzas.add(pizza2);
    }
    @Test
    public void givenNothingWhenGettingAllPizzasReturnAllPizzas(){
        Mockito.when(pizzaDao.findAll()).thenReturn(pizzas);

        final List<Pizza> allPizzas = pizzaService.getAllPizzas();
        Assertions.assertEquals(allPizzas.get(0).getId(), pizzas.get(0).getId());
    }
    @Test
    public void givenPizzaPathWhenGettingImageByPizzaReturnImage(){
        Mockito.when(pizzaDao.getPizzaImageByPath(ArgumentMatchers.anyString())).thenReturn(pizza1);
        final byte[] pizzaImage = pizzaService.getPizzaImage(ArgumentMatchers.anyString());
        Assertions.assertEquals(pizzaImage, pizza1.getData());
    }
    @Test
    public void givenPizzaIdWhenDeletingPizzaByIdReturnAllPizzas(){
        Mockito.when(pizzaDao.getOne(ArgumentMatchers.anyInt())).thenReturn(pizza1);
        Mockito.doNothing().when(pizzaDao).delete(pizza1);
        pizzas.remove(pizza1);
        Mockito.when(pizzaDao.findAll()).thenReturn(pizzas);
        final List<Pizza> actualPizzas = pizzaService.deletePizza(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualPizzas.get(0).getId(), 2);
    }
    @Test
    public void givenPageRequestWhenGettingSortedPizzasReturnPizzaDto(){
        final PizzaDto pizzaDto = new PizzaDto(pizzas, pizzas.size(), pizzas.size(), pizzas.size() / 2, 0);
        final PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Order.asc(pizza1.getName())));

        Mockito.when(pizzaDao.findAll(pageRequest)).thenReturn(new PageImpl<Pizza>(pizzas, pageRequest, 2));
        final PizzaDto sortedPizzas = pizzaService.getSortedPizzas(pageRequest);
        Assertions.assertEquals(sortedPizzas.getPizzas().get(0).getId(), pizzaDto.getPizzas().get(0).getId());
    }
}
