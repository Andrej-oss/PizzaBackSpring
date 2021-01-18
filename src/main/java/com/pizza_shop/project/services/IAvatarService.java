package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Avatar;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAvatarService {

    Avatar saveAvatar(int id, Avatar avatar, MultipartFile file);
    Avatar updateAvatar(int id, MultipartFile file);
    Avatar getAvatar(int id);
    List<Avatar> getAllAvatars();
    boolean deleteAvatar(int id);

    byte[] getAvatarByPath(String path);

    Avatar getAvatarByUserId(int id);
}
