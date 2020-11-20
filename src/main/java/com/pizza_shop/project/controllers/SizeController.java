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
    @GetMapping(value = "/size/image/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImagePizzaBySize(@PathVariable String path){
        return sizePizzaService.getSizeImageByPath(path);
    }
}
