package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvatarDao extends JpaRepository<Avatar, Integer> {
    Avatar getAvatarByPath(String path);

    Avatar findAvatarByUserId(int id);
}
