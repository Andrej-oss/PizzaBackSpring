package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseDao extends JpaRepository<Purchase, Integer> {
}
