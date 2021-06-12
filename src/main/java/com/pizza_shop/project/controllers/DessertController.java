package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Dessert;
import com.pizza_shop.project.services.IDessertService;
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
public class DessertController {

    private IDessertService dessertService;

    @Autowired
    public DessertController(IDessertService dessertService) {
        this.dessertService = dessertService;
    }
    @PostMapping(value = "api/dessert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Dessert> saveDessert(@ModelAttribute("dessert") @Valid Dessert dessert, MultipartFile image){
        log.info("Handling Post/ dessert with body " + dessert + " and image " + image);
        return dessertService.saveDessert(dessert, image);
    }
    @PutMapping(value = "api/dessert/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Dessert> updateDessert(@PathVariable int id, Dessert dessert, MultipartFile image){
        log.info("Handling Put/ dessert update with id " + id + " with new body " + dessert + " and image " + image);
        return dessertService.updateDessert(id, dessert, image);
    }
    @GetMapping(value = "api/dessert/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageByPath(@PathVariable String path){
        log.info("Handling Get/ dessert image by path " + path);
        return dessertService.getImageByPath(path);
    }
    @GetMapping("api/dessert")
    public List<Dessert> getAllDesserts(){
        log.info("Handling Get/ desserts all");
        return dessertService.getAllDesserts();
    }
    @DeleteMapping("api/dessert/{id}")
    public List<Dessert> deleteDessert(@PathVariable int id){
        log.info("Handling Delete/ dessert by id " + id);
        return dessertService.deleteDessert(id);
    }
}
