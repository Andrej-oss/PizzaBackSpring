package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.config.StoragePizzaConfig;
import com.pizza_shop.project.dao.PizzaDao;
import com.pizza_shop.project.dto.PizzaDto;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.services.IPizzaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PizzaService implements IPizzaService {

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private StoragePizzaConfig storagePizzaConfig;

    private Path rootFolder;

    @Override
    public Pizza createPizza(Pizza pizza, MultipartFile file) {
        final String name = pizza.getName();
        final String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().indexOf("."));
        Path pathFile = Paths.get(name + extension).normalize();
        pizza.setPath(pathFile.toString());
        try {
            pizza.setData(file.getBytes());
            Files.copy(file.getInputStream(), rootFolder.resolve(pathFile));
        } catch (IOException e) {
            log.warn("Unable to copy file " + e.getMessage());
        }
        this.pizzaDao.save(pizza);
        return pizza;
    }

    @PostConstruct
    public void init() {
        try {
            rootFolder = Paths.get(storagePizzaConfig.getLocation()).toAbsolutePath().normalize();
            if (!Files.isDirectory(rootFolder)) {
                Files.createDirectory(rootFolder);
            }
        } catch (IOException e) {
            log.warn("Unable to create folder " + e.getMessage());
        }
    }

    @Override
    public List<Pizza> updatePizza(int id, Pizza pizza, MultipartFile image) {
        final Pizza onePizza = pizzaDao.getOne(id);
        if (onePizza != null) {
            onePizza.setIngredients(pizza.getIngredients());
            onePizza.setNewPizza(pizza.isNewPizza());
            onePizza.setDescription(pizza.getDescription());
            onePizza.setName(pizza.getName());
            onePizza.setPrice(pizza.getPrice());
            if (image != null){
                final String name =  pizza.getName();
                final String fileExtension = Objects.requireNonNull(image.getOriginalFilename())
                        .substring(image.getOriginalFilename().indexOf("."));
                final Path path = Paths.get(name + fileExtension).normalize();
                onePizza.setPath(path.toString());
                try {
                    onePizza.setData(image.getBytes());
                    Files.deleteIfExists(rootFolder.resolve(path));
                    Files.copy(image.getInputStream(), rootFolder.resolve(path));
                } catch (IOException e) {
                    log.warn("Unable to copy file " + e.getMessage());
                }
            }
            pizzaDao.flush();
        }
        return pizzaDao.findAll();
    }

    @Override
    public byte[] getPizzaImage(String path) {
        return pizzaDao.getPizzaImageByPath(path).getData();
    }

    @Override
    public List<Pizza> getAllPizzas() {
        return pizzaDao.findAll();
    }

    @Override
    public List<Pizza> deletePizza(int id) {
        final Pizza pizza = pizzaDao.getOne(id);
        pizzaDao.delete(pizza);
        return pizzaDao.findAll();
    }

    @Override
    public PizzaDto getSortedPizzas(PageRequest pageRequest) {
        final Page<Pizza> pizzas = pizzaDao.findAll(pageRequest);
        return new PizzaDto(pizzas.getContent(), pizzas.getTotalElements(), pizzas.getSize(), pizzas.getTotalPages(), pizzas.getNumber());
    }

    @Override
    public Pizza getPizza(int id) {
        return pizzaDao.getOne(id);
    }
}
