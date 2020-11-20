package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.config.StoragePizzaSizeConfig;
import com.pizza_shop.project.dao.PizzaDao;
import com.pizza_shop.project.dao.SizeDao;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.Size;
import com.pizza_shop.project.services.ISizePizzaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SizeServiceService implements ISizePizzaService {

    @Autowired
    private SizeDao sizeDao;

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private StoragePizzaSizeConfig storagePizzaSizeConfig;

    private Path rootFolder;

    @PostConstruct
    public void init() {
        rootFolder = Paths.get(storagePizzaSizeConfig.getLocation()).toAbsolutePath().normalize();
        try {
            Files.createDirectory(rootFolder);
        } catch (IOException e) {
            log.warn("Enable to create directory " + e.getMessage());
        }
    }

    @Override
    public List<Size> getAllSizes() {
        return sizeDao.findAll();
    }

    @Override
    public Size getSize(int id) {
        return null;
    }

    @Override
    public List<Size> createSize(Size size, int id, MultipartFile file) {
        final String sizeName = size.getName();
        final String extension = Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().indexOf("."));
        final Pizza pizza = this.pizzaDao.getOne(id);
        final String pizzaName = pizza.getName();
        final Path pathSize = Paths.get(pizzaName + sizeName + extension);
        size.setPizza(pizza);
        size.setPath(pathSize.toString());
        try {
            size.setData(file.getBytes());
            Files.copy(file.getInputStream(), rootFolder.resolve(pathSize));
        } catch (IOException e) {
            log.warn("Unable to copy image file " + e.getMessage());
        }
        this.sizeDao.save(size);
        return sizeDao.findAll();
    }

    @Override
    public Size upDateSize(int id) {
        return null;
    }

    @Override
    public void deleteSize(int id) {

    }
    @Override
    public byte[] getSizeImageByPath(String path) {
        final Size sizeByPath = sizeDao.getImageByPath(path);
        return sizeByPath.getData();
    }
}
