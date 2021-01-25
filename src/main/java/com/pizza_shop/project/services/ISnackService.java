package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Snack;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISnackService {

    Snack getSnack(int id);
    List<Snack> getAllSnacks();
    List<Snack> saveSnack(Snack snack, MultipartFile image);
    List<Snack> updateSnake(int id, Snack snack, MultipartFile image);
    List<Snack> deleteSnack(int id);

    byte[] getImageByPath(String path);
}
