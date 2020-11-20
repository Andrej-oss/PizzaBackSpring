package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISizePizzaService {

    List<Size> getAllSizes();
    Size getSize(int id);
    List<Size> createSize(Size size, int id, MultipartFile file);
    Size upDateSize(int id);
    void deleteSize(int id);

    byte[] getSizeImageByPath(String path);
}
