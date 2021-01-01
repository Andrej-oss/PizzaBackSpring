package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseDao extends JpaRepository<Purchase, Integer> {
    List<Purchase> getAllPurchasesByUserId(int id);
}
