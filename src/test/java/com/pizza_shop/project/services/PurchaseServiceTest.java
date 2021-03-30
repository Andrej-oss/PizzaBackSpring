package com.pizza_shop.project.services;

import com.pizza_shop.project.dao.PurchaseDao;
import com.pizza_shop.project.dto.PurchasePageDto;
import com.pizza_shop.project.entity.Purchase;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.impl.PurchaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.invocation.ArgumentMatcherAction;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseDao purchaseDao;
    @InjectMocks
    private PurchaseService purchaseService;

    private List<Purchase> purchases;
    private Purchase purchase1;
    private Purchase purchase2;
    private User user;

    @BeforeEach
    public void init(){
        purchases = new ArrayList<Purchase>();
        user =  new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        purchase1 = new Purchase(1, "pepperoni", "pepperoni, mozzarella, pepper", 348.00, 1, 22, user, 1, 0, 0, 0, 1L);
        purchase2 = new Purchase(2, "coca cola", "coca cola 0.5", 0.5, 11, 4, user, 0, 1, 0, 0, 2L);
        purchases.add(purchase1);
        purchases.add(purchase2);
    }
    @Test
    public void givenPurchaseWhenInsertingPurchaseReturnAllPurchasesByUserId(){
       Purchase purchase3 = new Purchase(3, "fanta", "fanta 1l",0.5, 11, 4, user, 0, 2, 0, 0, 2L);
        Mockito.when(purchaseDao.save(purchase3)).thenReturn(purchase3);
        purchases.add(purchase3);
        Mockito.when(purchaseDao.getAllPurchasesByUserId(ArgumentMatchers.anyInt())).thenReturn(purchases);
        final List<Purchase> actualPurchases = purchaseService.createPurchase(purchase3);
        Assertions.assertEquals(actualPurchases.get(2).getId(), 3);
    }
    @Test
    public void givenPageRequestWhenGettingAllPurchasesReturnPurchasePageDto(){
        final PurchasePageDto purchasePageDto = new PurchasePageDto(purchases, purchases.size(), purchases.size() / 2, purchases.size(), 0);
        final PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Order.desc(purchase1.getName())));
        Mockito.when(purchaseDao.findAll(pageRequest)).thenReturn(new PageImpl<Purchase>(purchases, pageRequest, purchases.size()));
        final PurchasePageDto allPurchases = purchaseService.getAllPurchases(pageRequest);
        Assertions.assertEquals(allPurchases.getPurchases().get(0).getName(), purchasePageDto.getPurchases().get(0).getName());
    }
    @Test
    public void givenPurchaseIdWhenGettingPurchaseReturnPurchase(){
        Mockito.when(purchaseDao.getOne(ArgumentMatchers.anyInt())).thenReturn(purchase1);

        final Purchase purchase = purchaseService.getPurchase(ArgumentMatchers.anyInt());
        Assertions.assertEquals(purchase.getName(), purchase1.getName());
    }
    @Test
    public void givenPurchaseIdWhenDeletingPurchaseReturnTrue(){
        Mockito.when(purchaseDao.getOne(ArgumentMatchers.anyInt())).thenReturn(purchase1);
        Mockito.doNothing().when(purchaseDao).delete(purchase1);
        purchases.remove(purchase1);
        final boolean b = purchaseService.deletePurchase(ArgumentMatchers.anyInt());
        Assertions.assertTrue(b);
    }
    @Test
    public void givenUserIdWhenGettingAllPurchasesByUserIdReturnAllPurchases(){
        Mockito.when(purchaseDao.getAllPurchasesByUserId(ArgumentMatchers.anyInt())).thenReturn(purchases);

        final List<Purchase> allPurchasesByUserId = purchaseService.getAllPurchasesByUserId(ArgumentMatchers.anyInt());
        Assertions.assertEquals(allPurchasesByUserId.get(0).getName(), purchases.get(0).getName());
    }
}
