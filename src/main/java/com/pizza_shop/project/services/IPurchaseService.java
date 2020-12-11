package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Purchase;

import java.util.List;

public interface IPurchaseService {
    boolean createPurchase(Purchase purchase);
    Purchase updatePurchase(int id);
    List<Purchase> getAllPurchases();
    Purchase getPurchase(int id);
    void deletePurchase(int id);
}
