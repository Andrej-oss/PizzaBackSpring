package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Promotion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPromotionService {

    Promotion getPromotion(int id);
    List<Promotion> getAllPromotions();
    void savePromotion(Promotion promotion, MultipartFile image);
    void updatePromotion(int id, MultipartFile image);
    void deletePromotion(int id);

    byte[] getImageByPath(String path);
}
