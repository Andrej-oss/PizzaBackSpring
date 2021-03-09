package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.config.StoragePromoConfig;
import com.pizza_shop.project.dao.PromotionDao;
import com.pizza_shop.project.entity.Promotion;
import com.pizza_shop.project.services.IPromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class PromotionService implements IPromotionService {

    @Autowired
    private PromotionDao promotionDao;

    @Autowired
    private StoragePromoConfig storagePromoConfig;

    private Path rootFolder;

    @PostConstruct
    public void Init(){
        try {
            rootFolder = Paths.get(storagePromoConfig.getLocation()).toAbsolutePath().normalize();
            Files.createDirectory(rootFolder);
        } catch (IOException e) {
            log.warn("Unable to create folder " + e.getMessage());
        }
    }

    @Override
    public Promotion getPromotion(int id) {
        return promotionDao.getOne(id);
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionDao.findAll();
    }

    @Override
    public List<Promotion> savePromotion(Promotion promotion, MultipartFile image) {
        if (image != null && promotion != null){
            final String extension = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().indexOf("."));
            final Path path = Paths.get(promotion.getName() + extension).normalize();
            try {
                promotion.setPath(promotion.getName());
                promotion.setData(image.getBytes());
                promotionDao.save(promotion);
                Files.deleteIfExists(rootFolder.resolve(path));
                Files.copy(image.getInputStream(), rootFolder.resolve(path));
            } catch (IOException e) {
                log.warn("Unable to copy file " + e.getMessage());
            }
        }
        return promotionDao.findAll();
    }

    @Override
    public void updatePromotion(int id, MultipartFile image) {
        final Promotion promotion = getPromotion(id);
        if (promotion != null && image != null){
            final String name = promotion.getName();
            final String extension = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().indexOf('.'));
            final Path path = Paths.get(name + extension).toAbsolutePath().normalize();
            try {
                promotion.setData(image.getBytes());
                promotionDao.save(promotion);
                Files.deleteIfExists(rootFolder.resolve(path));
                Files.copy(image.getInputStream(), rootFolder.resolve(path));
                promotionDao.flush();
            } catch (IOException e) {
                log.warn("Unable to copy file " + e.getMessage());
            }
        }
    }

    @Override
    public void deletePromotion(int id) {
        final Promotion promotion = getPromotion(id);
        promotionDao.delete(promotion);
    }

    @Override
    public byte[] getImageByPath(String path) {
        final Promotion promotion = promotionDao.getPromotionByPath(path);
        return promotion.getData();
    }
}
