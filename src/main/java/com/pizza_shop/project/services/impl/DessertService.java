package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.config.StorageDessertConfig;
import com.pizza_shop.project.dao.DessertDao;
import com.pizza_shop.project.entity.Dessert;
import com.pizza_shop.project.entity.Snack;
import com.pizza_shop.project.services.IDessertService;
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
public class DessertService implements IDessertService {

    @Autowired
    private DessertDao dessertDao;

    @Autowired
    private StorageDessertConfig storageDessertConfig;

    private Path rootFolder;

    @PostConstruct
    public void Init(){
        rootFolder = Paths.get(storageDessertConfig.getLocation()).toAbsolutePath().normalize();
        if (!Files.isDirectory(rootFolder)){
            try {
                Files.createDirectory(rootFolder);
            } catch (IOException e) {
                log.warn("Unable to create folder " + e.getMessage());
            }
        }
    }

    @Override
    public List<Dessert> saveDessert(Dessert dessert, MultipartFile image) {
        if (image != null && dessert != null) {
            final String extension = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().indexOf("."));
            final Path path = Paths.get(dessert.getName() + extension).toAbsolutePath().normalize();
            try {
               Files.deleteIfExists(rootFolder.resolve(path));
               Files.copy(image.getInputStream(), rootFolder.resolve(path));
               dessert.setData(image.getBytes());
            } catch (IOException e) {
                log.warn("Unable to copy file " + e.getMessage());
            }
            dessertDao.save(dessert);
        }
        return dessertDao.findAll();
    }

    @Override
    public Dessert getDessert(int id) {
        return dessertDao.getOne(id);
    }

    @Override
    public List<Dessert> getAllDesserts() {
        return dessertDao.findAll();
    }

    @Override
    public List<Dessert> updateDessert(int id, Dessert dessert, MultipartFile image) {
        final Dessert dessert1 = dessertDao.getOne(id);
        if (image != null && dessert1 != null) {
            final String dessertName = dessert.getName();
            final String volume = dessert.getDescription();
            final String extension = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().indexOf("."));
            final Path path = Paths.get((dessertName + volume) + extension).normalize();
            try {
                Files.deleteIfExists(rootFolder.resolve(path));
                Files.copy(image.getInputStream(), rootFolder.resolve(path));
                dessert1.setData(image.getBytes());
                dessert1.setVolume(dessert.getVolume());
                dessert1.setName(dessert.getName());
                dessert1.setDescription(dessert.getDescription());
                dessert1.setPrice(dessert.getPrice());
                dessert1.setPath(dessert.getPath());
                dessertDao.save(dessert1);
            } catch (IOException e) {
                log.warn("Unable to copy file " + e.getMessage());
            }
            dessertDao.flush();
        }
        else if(image == null && dessert1 != null){
            dessert1.setVolume(dessert.getVolume());
            dessert1.setName(dessert.getName());
            dessert1.setDescription(dessert.getDescription());
            dessert1.setPrice(dessert.getPrice());
            dessert1.setPath(dessert.getPath());
            dessertDao.save(dessert1);
            dessertDao.flush();
        }
        return dessertDao.findAll();
    }

    @Override
    public List<Dessert> deleteDessert(int id) {
        final Dessert dessert = dessertDao.getOne(id);
        dessertDao.delete(dessert);
        return dessertDao.findAll();
    }

    @Override
    public byte[] getImageByPath(String path) {
        final Dessert dessertByPath = dessertDao.getDessertByPath(path);
        return dessertByPath.getData();
    }
}
