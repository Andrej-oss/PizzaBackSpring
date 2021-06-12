package com.pizza_shop.project.controllers;

import com.pizza_shop.project.dto.PizzaDto;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.services.IPizzaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200/api")
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
         log.info("Handling PostRequest pizza controller with body " + pizza);
         return  pizzaService.createPizza(pizza, image);
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
    @GetMapping("/pizza/sort")
    public PizzaDto getSortedPizzas(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "9") int size,
                                    @RequestParam String type,
                                    @RequestParam String sort){
        log.info("Handling all pizzas in /Get with sorting by " + size + " and page " + page  + " and type " + type + " and sort " + sort);
        PageRequest pageRequest = null;
            if (type.equals("desc")){
                pageRequest = PageRequest.of(page, size,Sort.by(sort).descending());
        }
            else if (type.equals("asc")){
                pageRequest = PageRequest.of(page, size, Sort.by(sort).ascending());
            }
            return pizzaService.getSortedPizzas(pageRequest);
    }
    @DeleteMapping("/pizza/{id}")
    public List<Pizza> deletePizza(@PathVariable int id){
        log.info("Handling deleting pizza with id = " + id);
        return pizzaService.deletePizza(id);
    }
    @PutMapping(value = "/pizza/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<Pizza> upDatePizza(@PathVariable int id, @Valid Pizza pizza, MultipartFile image){
        log.info("Handling updating pizza with id: " + id);
        return pizzaService.updatePizza(id, pizza, image);
    }
}
