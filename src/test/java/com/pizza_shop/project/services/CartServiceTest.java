package com.pizza_shop.project.services;

import com.pizza_shop.project.dao.CartDao;
import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.entity.Cart;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.impl.CartService;
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

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartDao cartDao;
    @Mock
    private UserDao userDao;

    private List<Cart> carts;
    private  Cart cart1;
    private  Cart cart2;
    private Cart cart3;
    private  User user;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public  void init(){
        carts = new ArrayList<Cart>();
        user =  new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        cart1 = new Cart(1, "Pizza pepperoni, cheddar", 20, "Small", 32, 340.00, 1, 0, 0, 0, user);
        cart2 = new Cart(2, "Coca cola", 7, null, 32, 0.5, 0, 1, 0, 0, user);
        carts.add(cart1);
        carts.add(cart2);
    }
    @Test
    public void givenNothingWhenGettingAllCartsReturnAllCarts(){
        Mockito.when(cartDao.findAll()).thenReturn(carts);

        final List<Cart> actualCarts = cartService.getAllCartElements();
        Assertions.assertEquals(actualCarts.get(0).getId(), carts.get(0).getId());
        Assertions.assertEquals(actualCarts.get(1).getDescription(), carts.get(1).getDescription());
    }
    @Test
    public void givenCartIdWhenGettingCartByIdReturnCart(){
        Mockito.when(cartDao.getOne(ArgumentMatchers.anyInt())).thenReturn(cart1);

        final Cart actualCart = cartService.getOneCartElement(ArgumentMatchers.anyInt());
        Assertions.assertEquals(actualCart.getId(), cart1.getId());
    }
    @Test
    public void givenCartWithNotNullUserWhenInsertingCartReturnAllCarts(){
       Cart cart3 = new Cart(3, "Pizza pepperoni, cheddar", 25, "Large", 32, 580.00, 1, 0, 0, 0, null);
       carts.add(cart3);
       Mockito.when(userDao.getOne(ArgumentMatchers.anyInt())).thenReturn(user);
       cart3.setUser(user);
       Mockito.when(cartDao.save(ArgumentMatchers.any(Cart.class))).thenReturn(cart3);
       Mockito.when(cartDao.findAllCartsByUserId(ArgumentMatchers.anyInt())).thenReturn(carts);
        final List<Cart> cartElements = cartService.createCartElement((cart3), 1);
        Assertions.assertEquals(cartElements.get(2).getId(), carts.get(2).getId());
    }
    @Test
    public void givenCartIdWhenDeletingCartReturnSuccessfulResponse(){
        Mockito.when(cartDao.getOne(ArgumentMatchers.anyInt())).thenReturn(cart1);
        Mockito.doNothing().when(cartDao).delete(ArgumentMatchers.any(Cart.class));
        cartService.deleteCartElement(cart1.getId());
        carts.remove(cart1);
        Assertions.assertEquals(carts.get(0).getId(), 2);
    }
    @Test
    public void givenUserIdWhenGettingAllCartsByUserReturnAllCartsByUserId(){
        Mockito.when(cartDao.findAllCartsByUserId(ArgumentMatchers.anyInt())).thenReturn(carts);

        final List<Cart> allPizzasByUserId = cartService.getAllPizzasByUserId(ArgumentMatchers.anyInt());
        Assertions.assertEquals(allPizzasByUserId.get(0).getId(), carts.get(0).getId());
        Assertions.assertEquals(allPizzasByUserId.get(1).getId(), carts.get(1).getId());
    }
    @Test
    public void givenCartIdAndPizzaPriceWhenAddingCartElementReturnSuccessfulResponse(){
        Mockito.when(cartDao.getOne(ArgumentMatchers.anyInt())).thenReturn(cart1);
        final @Positive int amount = cart1.getAmount();
        final @Positive double price = cart1.getPrice();
        cartService.addElementInCart(cart1.getId(),  8);
        Assertions.assertEquals(cart1.getPrice(), price + 8);
    }
    @Test
    public void givenCartIdAndPriceWhenRemovingCartElementFromCartReturnSuccessfulResponse(){
        Mockito.when(cartDao.getOne(ArgumentMatchers.anyInt())).thenReturn(cart1);
        final @Positive double price = cart1.getPrice();
        final @Positive int amount = cart1.getAmount();
        cartService.removePizzaInCart(cart1.getId(), 7);
        Assertions.assertEquals(cart1.getAmount(), amount - 1);
        Assertions.assertEquals(cart1.getPrice(), price - 7);
    }
}
