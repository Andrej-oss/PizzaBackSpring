package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.PurchaseDao;
import com.pizza_shop.project.entity.Purchase;
import com.pizza_shop.project.services.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService implements IPurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    @Override
    public boolean createPurchase(Purchase purchase) {
        purchaseDao.save(purchase);
        return true;
    }

    @Override
    public Purchase updatePurchase(int id) {
        return null;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseDao.findAll();
    }

    @Override
    public Purchase getPurchase(int id) {
        return purchaseDao.getOne(id);
    }

    @Override
    public void deletePurchase(int id) {

    }
}
