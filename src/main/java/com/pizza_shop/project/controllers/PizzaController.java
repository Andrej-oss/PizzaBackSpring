package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.services.IPizzaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class PizzaController {

    private IPizzaService pizzaService;

    @Autowired
    public PizzaController(IPizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping(value = "/pizza", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Pizza> SavePizza(@ModelAttribute @Valid Pizza pizza, MultipartFile image){
         pizzaService.createPizza(pizza, image);
         log.info("Handling PostRequest pizza controller with body " + pizza);
         return pizzaService.getAllPizzas();
    }
    @GetMapping(value = "/pizza/image/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPizzaImage(@PathVariable String path){
        log.info("Handling getting pizza image from path" + path);
        return pizzaService.getPizzaImage(path);
    }
    @GetMapping("/pizza")
    public List<Pizza> getAllPizza(){
        log.info("Handling getting all pizzas");
        return pizzaService.getAllPizzas();
    }


}
