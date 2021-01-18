package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.PurchaseDao;
import com.pizza_shop.project.dto.PurchasePageDto;
import com.pizza_shop.project.entity.Purchase;
import com.pizza_shop.project.services.IPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService implements IPurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    @Override
    public List<Purchase> createPurchase(Purchase purchase) {
        purchaseDao.save(purchase);
        return this.getAllPurchasesByUserId(purchase.getId());
    }

    @Override
    public Purchase updatePurchase(int id) {
        return null;
    }

    @Override
    public PurchasePageDto getAllPurchases(PageRequest pageRequest) {
        final Page<Purchase> all = purchaseDao.findAll(pageRequest);
        return new PurchasePageDto(all.getContent(), all.getTotalElements(), all.getSize(), all.getTotalPages(), all.getNumber());
    }

    @Override
    public Purchase getPurchase(int id) {
        return purchaseDao.getOne(id);
    }

    @Override
    public boolean deletePurchase(int id) {
        final Purchase purchase = purchaseDao.getOne(id);
        if (purchase == null){
            return false;
        }
        purchaseDao.delete(purchase);
        return true;
    }

    @Override
    public List<Purchase> getAllPurchasesByUserId(int id) {
        return purchaseDao.getAllPurchasesByUserId(id);    }
}
