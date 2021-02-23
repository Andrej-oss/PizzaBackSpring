package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Ingredient;
import com.pizza_shop.project.services.IIngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class IngredientController {

    private IIngredientService ingredientService;

    @Autowired
    public IngredientController(IIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @PostMapping(value = "/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Ingredient> saveIngredient(@ModelAttribute @Valid Ingredient ingredient, MultipartFile image){
        log.info("handling Post /ingredient from with object" + ingredient);
        return this.ingredientService.createIngredient(ingredient, image);
    }
    @PutMapping(value = "/ingredient/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Ingredient> updateIngredient(@PathVariable int id, Ingredient ingredient, MultipartFile file){
        log.info("handling updating ingredient by id " + id + "with ingredient " + ingredient);
        return ingredientService.updateIngredient(id, ingredient, file);
    }
    @GetMapping(value = "/ingredient/image/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable String path){
        log.info("Handling getting image from path" + path);
        return this.ingredientService.getImageByPath(path);
    }
    @GetMapping("/ingredient")
    public List<Ingredient> getAllIngredients(){
        log.info("Handling getting all ingredients");
        return this.ingredientService.getAllIngredients();
    }
    @DeleteMapping("/ingredient/{id}")
    public List<Ingredient> deleteIngredient(@PathVariable int id){
        log.info("Handling deleting ingredient with id: " + id);
        return ingredientService.deleteIngredient(id);
    }
}
