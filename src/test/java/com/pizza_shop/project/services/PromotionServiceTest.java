package com.pizza_shop.project.services;

import com.pizza_shop.project.config.StoragePromoConfig;
import com.pizza_shop.project.dao.PromotionDao;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.Promotion;
import com.pizza_shop.project.services.impl.PromotionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTest {

    @Mock
    private PromotionDao promotionDao;
    @Mock
    private StoragePromoConfig storagePromoConfig;
    @InjectMocks
    private PromotionService promotionService;

    private List<Promotion> promotions;
    private  Promotion promotion1;
    private  Promotion promotion2;


    @BeforeEach
    public  void init(){
        promotions = new ArrayList<Promotion>();
        promotion1 = new Promotion(1, "best", new byte[]{12,12,44,56,7,8}, "/best");
        promotion2 = new Promotion(2, "bestFromTheBest", new byte[]{12,-56,44,116,7,89}, "/bestofthebest");
        promotions.add(promotion1);
        promotions.add(promotion2);
    }
    @Test
    public void givenNothingWhenGettingAllPromotionsReturnAllPromotions(){
        Mockito.when(promotionDao.findAll()).thenReturn(promotions);

        final List<Promotion> allPromotions = promotionService.getAllPromotions();
        Assertions.assertEquals(allPromotions.get(0).getName(), promotions.get(0).getName());
    }
    @Test
    public void givenPromotionIdWhenGettingPromotionByIdReturnPromotion(){
        Mockito.when(promotionDao.getOne(ArgumentMatchers.anyInt())).thenReturn(promotion1);

        final Promotion promotion = promotionService.getPromotion(ArgumentMatchers.anyInt());
        Assertions.assertEquals(promotion.getName(), promotion1.getName());
    }
    @Test
    public void givenPathPromotionWhenGettingPromotionImageReturnImage(){
        Mockito.when(promotionDao.getPromotionByPath(ArgumentMatchers.anyString())).thenReturn(promotion1);

        final byte[] imageByPath = promotionService.getImageByPath(ArgumentMatchers.anyString());
        Assertions.assertEquals(imageByPath, promotion1.getData());
    }
    @Test
    public void givenPromotionIdWhenDeletingPromotionReturnAllPromotions(){
        Mockito.when(promotionDao.getOne(ArgumentMatchers.anyInt())).thenReturn(promotion1);
        Mockito.doNothing().when(promotionDao).delete(promotion1);
        promotions.remove(promotion1);
        promotionService.deletePromotion(ArgumentMatchers.anyInt());
        Assertions.assertEquals(promotions.get(0).getId(), 2);

    }
}
