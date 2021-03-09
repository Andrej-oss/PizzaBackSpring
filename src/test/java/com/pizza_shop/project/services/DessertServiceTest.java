package com.pizza_shop.project.services;

import com.pizza_shop.project.config.StorageDessertConfig;
import com.pizza_shop.project.dao.DessertDao;
import com.pizza_shop.project.entity.Dessert;
import com.pizza_shop.project.services.impl.DessertService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DessertServiceTest {

    @Mock
    private DessertDao dessertDao;
    @Mock
    private StorageDessertConfig storageDessertConfig;
    @InjectMocks
    private DessertService dessertService;

    private List<Dessert> desserts;
    private Dessert dessert1;
    private Dessert dessert2;
    private  Path rootFolder;
    @BeforeEach
    public void init(){
        desserts = new ArrayList<Dessert>();
        dessert1 = new Dessert(1, "napoleon", "Sweet and delicious", new byte[]{1,0,23,43,-11}, 12, "/napoleon", "1pcs.", 11);
        dessert2 = new Dessert(2, "cookies", "Sweet and chocolates", new byte[]{11,23,49,111,121}, 7, "/cookies", "120gr.", 111);
        desserts.add(dessert1);
        desserts.add(dessert2);
    }
    @Test
    public void givenNothingWhenGettingAllDessertsReturnAllDesserts(){
        Mockito.when(dessertDao.findAll()).thenReturn(desserts);

        final List<Dessert> allDesserts = dessertService.getAllDesserts();
        Assertions.assertEquals(allDesserts.get(0).getId(), desserts.get(0).getId());
        Assertions.assertEquals(allDesserts.get(1).getName(), desserts.get(1).getName());
    }
    @Test
    public void givenDessertIdWhenDeletingDessertReturnAllDesserts(){
        Mockito.when(dessertDao.getOne(ArgumentMatchers.anyInt())).thenReturn(dessert1);
        Mockito.doNothing().when(dessertDao).delete(ArgumentMatchers.any(Dessert.class));
        Mockito.when(dessertDao.findAll()).thenReturn(desserts);

        final List<Dessert> actualDesserts = dessertService.deleteDessert(ArgumentMatchers.anyInt());
        Assertions.assertEquals(desserts.get(0).getId(), actualDesserts.get(0).getId());
    }
    @Test
    public void givenPathDessertImageWhenGettingImageByPathReturnImage(){
        Mockito.when(dessertDao.getDessertByPath(ArgumentMatchers.anyString())).thenReturn(dessert1);

        final byte[] imageByPath = dessertService.getImageByPath(ArgumentMatchers.anyString());
        Assertions.assertEquals(imageByPath, dessert1.getData());
    }
    //todo
//    @Test
//    public void givenDessertIdAndValidDessertWithImageDessertWhenPuttingDessertThenReturnAllDesserts(){
//       Dessert newDessert2 = new Dessert(2, "shine cookies", "Sweet and chocolates", new byte[]{111,2,-49,111,121}, 9, "/shiny cookies", "120gr.", 111);
//        Mockito.when(dessertDao.getOne(ArgumentMatchers.anyInt())).thenReturn(dessert1);
//        MockMultipartFile image = new MockMultipartFile(
//                "image",
//                "WX20180207-134704@2x.png",
//                "image/png",
//                "pepperoniMedium.jpg".getBytes());
//       // rootFolder = Paths.get(storageDessertConfig.getLocation()).toAbsolutePath().normalize();
//
//        final List<Dessert> actualDesserts = dessertService.updateDessert(ArgumentMatchers.anyInt(), newDessert2, image);
//        Assertions.assertEquals(actualDesserts.get(1).getName(), newDessert2.getName());
//
//    }
}
