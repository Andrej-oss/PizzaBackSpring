package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Promotion;
import com.pizza_shop.project.services.IPromotionService;
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
public class PromotionController {

    private IPromotionService promotionService;

    @Autowired
    public PromotionController(IPromotionService promotionService) {
        this.promotionService = promotionService;
    }
    @PostMapping(value = "/promotion", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Promotion> savePromotion(@Valid Promotion promotion, MultipartFile image){
        log.info("handling Post /promotion with " + promotion + " and image " + image);
        promotionService.savePromotion(promotion, image);
        return promotionService.getAllPromotions();
    }
    @GetMapping(value = "/promotion/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageByPath(@PathVariable String path){
        log.info("handling Get /promotion image by path " + path);
        return promotionService.getImageByPath(path);
    }
    @GetMapping("/promotion")
    public List<Promotion> getAllPromotions(){
        log.info("handling Get /All promotion");
        return promotionService.getAllPromotions();
    }
    @PutMapping(value = "/promotion/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Promotion> updatePromotion(@PathVariable int id, MultipartFile image){
        log.info("handling Put /promotion with id " + id + " and image " + image);
        promotionService.updatePromotion(id, image);
        return promotionService.getAllPromotions();
    }
    @DeleteMapping("/promotion/{id}")
    public List<Promotion> deletePromotion(@PathVariable int id){
        log.info("handling Delete /promotion by id " + id);
        promotionService.deletePromotion(id);
        return promotionService.getAllPromotions();
    }
}
