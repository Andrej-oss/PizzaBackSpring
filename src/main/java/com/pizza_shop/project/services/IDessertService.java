package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Dessert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDessertService {

    List<Dessert> saveDessert(Dessert dessert, MultipartFile image);
    Dessert getDessert(int id);
    List<Dessert> getAllDesserts();
    List<Dessert> updateDessert(int id, Dessert dessert, MultipartFile image);
    List<Dessert> deleteDessert(int id);

    byte[] getImageByPath(String path);
}
