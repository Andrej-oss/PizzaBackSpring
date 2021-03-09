package com.pizza_shop.project.services;

import com.pizza_shop.project.config.StorageSnackConfig;
import com.pizza_shop.project.dao.SnackDao;
import com.pizza_shop.project.entity.Snack;
import com.pizza_shop.project.services.impl.SnackService;
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
public class SnackServiceTest {

    @Mock
    private SnackDao snackDao;
    @Mock
    private StorageSnackConfig storageSnackConfig;
    @InjectMocks
    private SnackService snackService;

    private List<Snack> snacks;
    private Snack snack1;
    private Snack snack2;

    @BeforeEach
    public void init(){
        snacks = new ArrayList<Snack>();
        snack1 = new Snack(1, "chipotle", "Good tasted snack with meat and cheese",
                6, "1pcs.", new byte[]{21, 0, 11, 23, 10, -1}, "/chipotle", 0);
        snack2 = new Snack(2, "tortilla", "Good tasted snack with meat and cheese",
                7, "1pcs.", new byte[]{21, 89, 11, 56, 121, 0}, "/tortilla", 11);
        snacks.add(snack1);
        snacks.add(snack2);
    }
    @Test
    public void givenNothingWhenGettingAllSnacksReturnAllSnacks(){
        Mockito.when(snackDao.findAll()).thenReturn(snacks);

        final List<Snack> allSnacks = snackService.getAllSnacks();
        Assertions.assertEquals(allSnacks.get(0).getPath(), snacks.get(0).getPath());
    }
    @Test
    public void givenSnackIdWhenGettingSnackByIdReturnSnack(){
        Mockito.when(snackDao.getOne(ArgumentMatchers.anyInt())).thenReturn(snack1);
        final Snack actualSnack = snackService.getSnack(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualSnack.getDescription(), snack1.getDescription());
    }
    @Test
    public void givenSnackPathWhenGettingSnackImageByPathReturnImage(){
        Mockito.when(snackDao.getSnackByPath(ArgumentMatchers.anyString())).thenReturn(snack1);

        final byte[] imageByPath = snackService.getImageByPath(ArgumentMatchers.anyString());
        Assertions.assertEquals(imageByPath, snack1.getData());
    }
    @Test
    public void givenSnackIdWhenDeletingSnackByIdReturnAllSnacks(){
        Mockito.when(snackDao.getOne(ArgumentMatchers.anyInt())).thenReturn(snack1);
        Mockito.doNothing().when(snackDao).delete(snack1);
        snacks.remove(snack1);
        Mockito.when(snackDao.findAll()).thenReturn(snacks);
        final List<Snack> actualSnacks = snackService.deleteSnack(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualSnacks.get(0).getId(), 2);
    }
}
