package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.config.StorageDrinkConfig;
import com.pizza_shop.project.dao.DrinkDao;
import com.pizza_shop.project.entity.Drink;
import com.pizza_shop.project.services.IDrinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DrinkService implements IDrinkService {

    @Autowired
    private DrinkDao drinkDao;
    @Autowired
    private StorageDrinkConfig storageDrinkConfig;

    private Path rootFolder;

    @PostConstruct
    public void Init() {
        try {
            rootFolder = Paths.get(storageDrinkConfig.getLocation()).toAbsolutePath().normalize();
            if (!Files.isDirectory(rootFolder)) {
                Files.createDirectories(rootFolder);
            }
        } catch (IOException e) {
            log.warn("Unable to create folder " + e.getMessage());
        }
    }
    @Override
    public Drink getDrink(int id) {
        return null;
    }

    @Override
    public List<Drink> getAllDrinks() {
        return drinkDao.findAll();
    }

    @Override
    public List<Drink> saveDrink(Drink drink, MultipartFile image) {
        if (image != null && drink != null){
            final String drinkName = drink.getName();
            final double volume = drink.getVolume();
            final String extension = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().indexOf("."));
            final Path path = Paths.get((drinkName + volume) + extension).normalize();
            try {
                Files.deleteIfExists(rootFolder.resolve(path));
                Files.copy(image.getInputStream(), rootFolder.resolve(path));
                drink.setData(image.getBytes());
                drink.setPath(drinkName + volume);
            } catch (IOException e) {
                log.warn("Unable to copy file " + e.getMessage());
            }
            drinkDao.save(drink);
        }
        return getAllDrinks();
    }

    @Override
    public List<Drink> updateDrink(int id, Drink drink, MultipartFile image) {
        final Drink drink1 = drinkDao.getOne(id);
        if (image != null && drink1 != null){
            final String drinkName = drink.getName();
            final double volume = drink.getVolume();
            final String extension = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().indexOf("."));
            final Path path = Paths.get((drinkName + volume) + extension).normalize();
            try {
                Files.deleteIfExists(rootFolder.resolve(path));
                Files.copy(image.getInputStream(), rootFolder.resolve(path));
                drink1.setData(image.getBytes());
                drink1.setName(drink.getName());
                drink1.setVolume(drink.getVolume());
                drink1.setPrice(drink.getPrice());
                drink1.setPath(drinkName + volume);
                drinkDao.save(drink1);
            } catch (IOException e) {
                log.warn("Unable to copy file " + e.getMessage());
            }
            drinkDao.flush();
        }
        else if (image == null && drink1 != null){
            final String drinkName = drink.getName();
            final double volume = drink.getVolume();
            drink1.setVolume(drink.getVolume());
            drink1.setPrice(drink.getPrice());
            drink1.setName(drink.getName());
            drink1.setPath(drinkName + volume);
            drinkDao.save(drink1);
            drinkDao.flush();
        }
        return getAllDrinks();
    }

    @Override
    public List<Drink> deleteDrink(int id) {
        final Drink drink = drinkDao.getOne(id);
        drinkDao.delete(drink);
        drinkDao.flush();
        return getAllDrinks();
    }

    @Override
    public byte[] getImageByPath(String path) {
        final Drink drinkByPath = drinkDao.getDrinkByPath(path);
        return drinkByPath.getData();
    }
}
