package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.config.StorageSnackConfig;
import com.pizza_shop.project.dao.SnackDao;
import com.pizza_shop.project.entity.Snack;
import com.pizza_shop.project.services.ISnackService;
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
public class SnackService implements ISnackService {

    @Autowired
    private SnackDao snackDao;

    @Autowired
    private StorageSnackConfig storageSnackConfig;

    private Path rootFolder;

    @PostConstruct
    public void Init(){
        rootFolder = Paths.get(storageSnackConfig.getLocation()).toAbsolutePath().normalize();
        if (!Files.isDirectory(rootFolder)){
            try {
                Files.createDirectory(rootFolder);
            } catch (IOException e) {
                log.warn("Unable to create folder " + e.getMessage());
            }
        }
    }

    @Override
    public Snack getSnack(int id) {
        return snackDao.getOne(id);
    }

    @Override
    public List<Snack> getAllSnacks() {
        return snackDao.findAll();
    }

    @Override
    public List<Snack> saveSnack(Snack snack, MultipartFile image) {
        if (snack != null && image != null){
            final String extension = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().indexOf("."));
            final Path path = Paths.get(snack.getPath() + extension).toAbsolutePath().normalize();
            try {
                Files.deleteIfExists(rootFolder.resolve(path));
                Files.copy(image.getInputStream(), rootFolder.resolve(path));
                snack.setData(image.getBytes());
                snackDao.save(snack);
            } catch (IOException e) {
                log.warn("Unable to copy file " + e.getMessage());
            }
        }
        return getAllSnacks();
    }

    @Override
    public List<Snack> updateSnake(int id, Snack snack, MultipartFile image) {
        final Snack snack1 = snackDao.getOne(id);
        if (image != null && snack1 != null) {
            final String drinkName = snack.getName();
            final String volume = snack.getDescription();
            final String extension = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().indexOf("."));
            final Path path = Paths.get((drinkName + volume) + extension).normalize();
            try {
                Files.deleteIfExists(rootFolder.resolve(path));
                Files.copy(image.getInputStream(), rootFolder.resolve(path));
                snack1.setData(image.getBytes());
                snack1.setVolume(snack.getVolume());
                snack1.setName(snack.getName());
                snack1.setDescription(snack.getDescription());
                snack1.setPrice(snack.getPrice());
                snack1.setPath(snack.getPath());
                snackDao.save(snack1);
            } catch (IOException e) {
                log.warn("Unable to copy file " + e.getMessage());
            }
            snackDao.flush();
        }
        else if (image == null && snack1 != null){
            snack1.setDescription(snack.getDescription());
            snack1.setPrice(snack.getPrice());
            snack1.setVolume(snack.getVolume());
            snack1.setName(snack.getName());
            snack1.setPath(snack.getPath());
            snackDao.save(snack1);
            snackDao.flush();
        }
        return snackDao.findAll();
    }

    @Override
    public List<Snack> deleteSnack(int id) {
        final Snack snack = getSnack(id);
        snackDao.delete(snack);
        return snackDao.findAll();
    }

    @Override
    public byte[] getImageByPath(String path) {
        final Snack snackByPath = snackDao.getSnackByPath(path);
        return snackByPath.getData();
    }
}
