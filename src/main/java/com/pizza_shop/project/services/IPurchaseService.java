package com.pizza_shop.project.services;

import com.pizza_shop.project.dto.PurchasePageDto;
import com.pizza_shop.project.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IPurchaseService {
    List<Purchase> createPurchase(Purchase purchase);
    Purchase updatePurchase(int id);
    PurchasePageDto getAllPurchases(PageRequest pageRequest);
    Purchase getPurchase(int id);
    boolean deletePurchase(int id);

    List<Purchase> getAllPurchasesByUserId(int id);
}
