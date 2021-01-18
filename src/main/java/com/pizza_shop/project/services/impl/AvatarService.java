package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.config.StorageUserConfig;
import com.pizza_shop.project.dao.AvatarDao;
import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.entity.Avatar;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.IAvatarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AvatarService implements IAvatarService {

    @Autowired
    private AvatarDao avatarDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StorageUserConfig storageUserConfig;

    private Path rootFolder;

    @PostConstruct
    public void Init(){
        try {
            rootFolder = Paths.get(storageUserConfig.getLocation()).toAbsolutePath().normalize();
            Files.createDirectory(rootFolder);
        } catch (IOException e) {
            log.warn("Enable to create folder " + e.getMessage() );
        }
    }

    @Override
    public Avatar saveAvatar(int id, Avatar avatar, MultipartFile file) {
        final User user = userDao.getOne(id);
        if (file != null && user != null){
            final String username = user.getUsername();
            final String extension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().indexOf("."));
            final Path imagePath = Paths.get(username + extension).normalize();
            try {
                Files.deleteIfExists(rootFolder.resolve(imagePath));
                Files.copy(file.getInputStream(), rootFolder.resolve(imagePath));
                avatar.setData(file.getBytes());
            } catch (IOException e) {
                log.warn("Enable to create file" + e.getMessage());
            }
            avatar.setPath(username);
            avatar.setUser(user);
        }
        this.avatarDao.save(avatar);
        return this.avatarDao.getOne(avatar.getId());
    }

    @Override
    public Avatar updateAvatar(int id, MultipartFile file) {
        final Avatar avatar = getAvatar(id);
        if (file != null && avatar != null){
            try {
                avatar.setData(file.getBytes());
            } catch (IOException e) {
                log.warn("Enable to save file" + e.getMessage());
            }
            avatarDao.flush();
        }
        return getAvatar(id);
    }

    @Override
    public Avatar getAvatar(int id) {
        return avatarDao.getOne(id);
    }

    @Override
    public boolean deleteAvatar(int id) {
        final Avatar avatar = avatarDao.getOne(id);
        avatarDao.delete(avatar);
        return true;
    }

    @Override
    public byte[] getAvatarByPath(String path) {
        final Avatar avatar = avatarDao.getAvatarByPath(path);
        return avatar.getData();
    }

    @Override
    public Avatar getAvatarByUserId(int id) {
        return  avatarDao.findAvatarByUserId(id);
    }

    @Override
    public List<Avatar> getAllAvatars() {
        return avatarDao.findAll();
    }
}
