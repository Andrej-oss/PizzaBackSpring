package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Size;
import com.pizza_shop.project.services.ISizePizzaService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class SizeController {

    private ISizePizzaService sizePizzaService;

    @Autowired
    public SizeController(ISizePizzaService sizePizzaService) {
        this.sizePizzaService = sizePizzaService;
    }

    @PostMapping(value = "/size/{pizzaId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Size> saveSizePizza(@PathVariable int pizzaId, @ModelAttribute Size size, MultipartFile file){
        log.info("Handling post PizzaSize with data " + size + file.getOriginalFilename());
        return this.sizePizzaService.createSize(size, pizzaId, file);
    }
    @PutMapping(value = "/size/{sizeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Size> updateSizePizza(@PathVariable int sizeId,Size size, MultipartFile image){
        return sizePizzaService.upDateSize(sizeId, size, image);
    }
    @GetMapping(value = "/size/image/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImagePizzaBySize(@PathVariable String path){
        log.info("Handling getting image pizza size by path " + path);
        return sizePizzaService.getSizeImageByPath(path);
    }
    @GetMapping(value = "/size/image/{path}/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageByName(@PathVariable String path, @PathVariable String name){
        log.info("Handling getting image size pizza by name " + name);
        return sizePizzaService.getSizeImageByNameType(path, name);
    }
    @GetMapping("/size/{pizzaId}/{name}")
    public Size getPizzaSizes(@PathVariable int pizzaId, @PathVariable String name){
        return sizePizzaService.getSizeByPizzaId(pizzaId, name);
    }
    @GetMapping("/size/{pizzaId}")
    public List<Size> getPizzaSizesByPizzaId(@PathVariable int pizzaId){
        log.info("Handling getting all sizes by pizza id " + pizzaId);
        return sizePizzaService.getAllSizesByPizzaId(pizzaId);
    }
    @DeleteMapping("/size/{id}")
    public List<Size> deletePizzaSize(@PathVariable int id){
        log.info("Handling deleting size by pizza id " + id);
        return sizePizzaService.deleteSize(id);
    }
}
