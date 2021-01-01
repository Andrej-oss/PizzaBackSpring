package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IPurchaseService {
    List<Purchase> createPurchase(Purchase purchase);
    Purchase updatePurchase(int id);
    Page<Purchase> getAllPurchases(PageRequest pageRequest);
    Purchase getPurchase(int id);
    boolean deletePurchase(int id);

    List<Purchase> getAllPurchasesByUserId(int id);
}
