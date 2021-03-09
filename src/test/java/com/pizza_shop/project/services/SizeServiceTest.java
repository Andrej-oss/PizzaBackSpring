package com.pizza_shop.project.services;

import com.pizza_shop.project.dao.PizzaDao;
import com.pizza_shop.project.dao.SizeDao;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.Size;
import com.pizza_shop.project.services.impl.SizeService;
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
public class SizeServiceTest {

    @Mock
    private SizeDao sizeDao;
    @Mock
    private PizzaDao pizzaDao;
    @InjectMocks
    private SizeService sizeService;

    private List<Size> sizes;
    private  Size size1;
    private  Size size2;
    private Pizza pizza;

    @BeforeEach
    public void init(){
        sizes = new ArrayList<Size>();
        pizza = new Pizza(1, true, "/scdcsd", "papa", "description", null, 10, "1,24,6", 4, null, null, null);
        size1 = new Size(1, "pepperoniSmall", 20, 320, 16, new byte[]{12, 32, 45, -11, 0, 43}, "/pepperoni20", pizza);
        size2 = new Size(2, "pepperoniLarge", 30, 700, 33, new byte[]{18, 22, 121, 0, 19, -67}, "/pepperoni30", pizza);
        sizes.add(size1);
        sizes.add(size2);
    }
    @Test
    public void givenNothingWhenGettingAllSizesReturnAllSizes(){
        Mockito.when(sizeDao.findAll()).thenReturn(sizes);

        final List<Size> allSizes = sizeService.getAllSizes();
        Assertions.assertEquals(allSizes.get(0).getName(), sizes.get(0).getName());
    }
    @Test
    public void givenSizeIdWhenGettingSizePizzaByIdReturnSize(){
        Mockito.when(sizeDao.getOne(ArgumentMatchers.anyInt())).thenReturn(size1);

        final Size actualSize = sizeService.getSize(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualSize.getName(), size1.getName());
    }
    @Test
    public void givenPathSizePizzaWhenGettingImageSizePizzaReturnImage(){
        Mockito.when(sizeDao.getImageByPath(ArgumentMatchers.anyString())).thenReturn(size1);

        final byte[] sizeImageByPath = sizeService.getSizeImageByPath(ArgumentMatchers.anyString());
        Assertions.assertEquals(sizeImageByPath, size1.getData());
    }
    @Test
    public void givenSizePizzaIdWhenDeletingPizzaReturnAllPizzas(){
        Mockito.when(sizeDao.getOne(ArgumentMatchers.anyInt())).thenReturn(size1);
        Mockito.doNothing().when(sizeDao).delete(size1);
        sizes.remove(size1);
        Mockito.when(sizeDao.getAllSizesByPizzaId(ArgumentMatchers.anyInt())).thenReturn(sizes);
        final List<Size> actualSizes = sizeService.deleteSize(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualSizes.get(0).getId(), sizes.get(0).getId());
    }
    @Test
    public void givenNameAndPathSizeWhenGettingImageSizePizzaByNameAndPathReturnImage(){
        Mockito.when(sizeDao.getImageByPathAndName(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(size1);

        final byte[] sizeImageByNameType = sizeService.getSizeImageByNameType(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
        Assertions.assertEquals(sizeImageByNameType, size1.getData());
    }
    @Test
    public void givenPizzaIdWhenGettingSizeByPizzaReturnAllPizzaSizes(){
        Mockito.when(sizeDao.getSizeByPizzaIdAndName(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString())).thenReturn(size1);

        final Size sizeByPizzaId = sizeService.getSizeByPizzaId(ArgumentMatchers.anyInt(), ArgumentMatchers.anyString());
        Assertions.assertEquals(sizeByPizzaId.getName(), size1.getName());
    }
    @Test
    public void givenPizzaIdWhenGettingAllSizesByPizzaReturnAllSizesByPizza(){
        Mockito.when(sizeDao.getAllSizesByPizzaId(ArgumentMatchers.anyInt())).thenReturn(sizes);

        final List<Size> allSizesByPizzaId = sizeService.getAllSizesByPizzaId(ArgumentMatchers.anyInt());
        Assertions.assertEquals(allSizesByPizzaId.get(0).getWeight(), sizes.get(0).getWeight());
    }
}
