package com.pizza_shop.project.controllers;


import com.pizza_shop.project.entity.Avatar;
import com.pizza_shop.project.services.IAvatarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200/api")
@Slf4j
public class AvatarController {

    private IAvatarService avatarService;

    @Autowired
    public AvatarController(IAvatarService avatarService) {
        this.avatarService = avatarService;
    }
    @PostMapping(value = "api/avatar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Avatar saveAvatar(@PathVariable int id, @ModelAttribute @Valid Avatar avatar, MultipartFile file){
        log.info("handling Post /avatar from with object" + avatar);
        return avatarService.saveAvatar(id, avatar, file);
    }
    @PutMapping(value = "api/avatar/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Avatar updateAvatar(@PathVariable int id, MultipartFile file){
        log.info("handling Put /avatar from id: " + id + " and this image " + file);
        return avatarService.updateAvatar(id, file);
    }
    @GetMapping(value = "api/avatar/image/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(value = HttpStatus.FOUND)
    public byte[] getAvatarImageByPath(@PathVariable String path){
        log.info("handling Get /avatar image by this path " + path);
        return avatarService.getAvatarByPath(path);
    }
    @GetMapping("api/avatar/{id}")
    public Avatar getAvatarByUserId(@PathVariable int id){
        log.info("handling Get /avatar  by user id " + id);
        return avatarService.getAvatarByUserId(id);
    }
    @GetMapping("api/avatar")
    public List<Avatar> getAllAvatars(){
        log.info("handling Get / all avatars");
        return avatarService.getAllAvatars();
    }
    @DeleteMapping("api/avatar/{id}")
    public boolean deleteAvatar(@PathVariable int id){
        log.info("handling Delete /avatar this avatar id " + id);
        return avatarService.deleteAvatar(id);
    }
}
