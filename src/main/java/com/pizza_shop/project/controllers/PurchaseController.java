package com.pizza_shop.project.controllers;

import com.pizza_shop.project.dto.PurchasePageDto;
import com.pizza_shop.project.entity.Purchase;
import com.pizza_shop.project.services.IPurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class PurchaseController {

    private IPurchaseService purchaseService;

    @Autowired
    public PurchaseController(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/purchase/{userId}")
    public List<Purchase> getPurchasesByUser(@PathVariable int userId){
        log.info("Handling getting all purchases in /Get with user id: " + userId);
        return purchaseService.getAllPurchasesByUserId(userId);
    }
    @GetMapping("/purchase")
    public PurchasePageDto getAllPurchases(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam String sort,
                                           @RequestParam String type){
        log.info("Handling all purchases in /Get with pagination with size " + size + " and page " + page  + " and type " + type);
        PageRequest pageRequest = null;
        if (type.equals("desc")) {
            pageRequest = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        else if (type.equals("asc")){
            pageRequest = PageRequest.of(page, size, Sort.by(sort).ascending());

        }
        return purchaseService.getAllPurchases(pageRequest);
    }
    @DeleteMapping("/purchase/{id}")
    public boolean deletePurchase(@PathVariable int id){
        log.info("Handling delete purchase by id:" + id);
        return purchaseService.deletePurchase(id);
    }
}
