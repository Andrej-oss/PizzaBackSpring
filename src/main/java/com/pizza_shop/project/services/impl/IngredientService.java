package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.config.StorageConfig;
import com.pizza_shop.project.dao.IngredientDao;
import com.pizza_shop.project.entity.Ingredient;
import com.pizza_shop.project.services.IIngredientService;
import com.pizza_shop.project.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class IngredientService implements IIngredientService {

    @Autowired
    private IngredientDao ingredientDao;

    @Autowired
    private StorageConfig storageConfig;

    private Path rootFolder;

    @PostConstruct
    public void init(){
        try {
            rootFolder = Paths.get(storageConfig.getLocation()).toAbsolutePath().normalize();
            Files.createDirectory(rootFolder);
        } catch (IOException e) {
            log.warn("Enable to create folder " + e.getMessage() );
        }
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient, MultipartFile image) {
        final String name = ingredient.getName();
        String extension = Objects.requireNonNull(image.getOriginalFilename())
                .substring(image.getOriginalFilename().indexOf('.'));
        final Path imagePath = Paths.get(name +  extension).normalize();
        try {
            ingredient.setData(image.getBytes());
            Files.copy(image.getInputStream(), rootFolder.resolve(imagePath));
        } catch (IOException e) {
            log.warn("Enable to create file" + e.getMessage());
        }
        ingredient.setPath(imagePath.toString());
        this.ingredientDao.save(ingredient);
        return this.ingredientDao.getOne(ingredient.getId());
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return this.ingredientDao.findAll();
    }

    @Override
    public Ingredient updateIngredient(Ingredient ingredient) {
        return null;
    }

    @Override
    public void deleteIngredient(int id) {

    }

    @Override
    public Ingredient getIngredient(int id) {
        return this.ingredientDao.getOne(id);
    }

    @Override
    public byte[] getImageByPath(String path) {
        return this.ingredientDao.getImageByPath(path).getData();
    }
}
