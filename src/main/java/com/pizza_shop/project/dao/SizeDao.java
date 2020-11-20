package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SizeDao extends JpaRepository<Size, Integer> {

    @Query("select s from Size s where s.path = :path")
    Size getImageByPath(String path);
}
