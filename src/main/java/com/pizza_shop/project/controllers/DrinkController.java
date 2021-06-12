package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Drink;
import com.pizza_shop.project.services.IDrinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/api")
@Slf4j
public class DrinkController {

    private IDrinkService drinkService;

    @Autowired
    public DrinkController(IDrinkService drinkService) {
        this.drinkService = drinkService;
    }
    @PostMapping(value = "api/drink", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Drink> saveDrink(@ModelAttribute("drink") @Valid Drink drink, MultipartFile image){
        log.info("handling Post /drink  " + drink + " and with image " + image);
        return drinkService.saveDrink(drink, image);
    }
    @PutMapping(value = "api/drink/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Drink> updateDrink(@PathVariable int id, @Valid Drink drink, MultipartFile image){
        log.info("handling Put /drink update by id " + id + " with body " + drink + " and with image " + image);
        return drinkService.updateDrink(id, drink, image);
    }
    @GetMapping(value = "api/drink/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageByPath(@PathVariable String path){
        log.info("handling Get /drink image by path " + path);
        return drinkService.getImageByPath(path);
    }
    @GetMapping("api/drink")
    public List<Drink> getAllDrinks(){
        log.info("handling Get /drinks all ");
        return drinkService.getAllDrinks();
    }
    @DeleteMapping("api/drink/{id}")
    public List<Drink> deleteDrink(@PathVariable int id){
        log.info("handling Delete /drink by id " + id);
        return drinkService.deleteDrink(id);
    }
}
