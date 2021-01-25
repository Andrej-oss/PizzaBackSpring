package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Snack;
import com.pizza_shop.project.services.ISnackService;
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
public class SnackController {

    private ISnackService snackService;

    @Autowired
    public SnackController(ISnackService snackService) {
        this.snackService = snackService;
    }

    @PostMapping(value = "/snack", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Snack> saveSnack(@Valid Snack snack, MultipartFile image){
        log.info("Handling Post/snack with body " + snack + " with image " + image);
        return snackService.saveSnack(snack, image);
    }
    @PutMapping(value = "/snack/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Snack> updateSnack(@PathVariable int id, @Valid Snack snack, MultipartFile image){
        log.info("Handling Put/snack by id " + id + " and with new body " + snack + " and image " + image);
        return snackService.updateSnake(id, snack, image);
    }
    @GetMapping(value = "/snack/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageByPath(@PathVariable String path){
        log.info("Handling Get/ snack image by path " + path);
        return snackService.getImageByPath(path);
    }
    @GetMapping("/snack")
    public List<Snack> getAllSnacks(){
        log.info("Handling Get/snack all");
        return snackService.getAllSnacks();
    }
    @DeleteMapping("/snack/{id}")
    public List<Snack> deleteSnack(@PathVariable int id){
        log.info("Handling Delete/ snack by id " + id);
        return snackService.deleteSnack(id);
    }
}
