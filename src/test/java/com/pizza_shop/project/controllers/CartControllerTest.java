package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Cart;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.CartService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartController.class)
@Import(SecurityConfig.class)
public class CartControllerTest {

    @MockBean
    private CartService cartService;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;

    private static List<Cart> carts;
    private static Cart cart1;
    private static Cart cart2;
    private Cart cart3;
    private static User user;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void init(){
        carts = new ArrayList<Cart>();
        user =  new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        cart1 = new Cart(1, "Pizza pepperoni, cheddar", 20, "Small", 32, 340.00, 1, 0, 0, 0, user);
        cart2 = new Cart(2, "Coca cola", 7, null, 32, 0.5, 0, 1, 0, 0, user);
        carts.add(cart1);
        carts.add(cart2);
    }
    @Test
    @WithMockUser
    public void givenValidCartBodyWhenInsertingCartElementReturnSuccessfulResponse() throws Exception{
        final Cart cart3 = new Cart(3, "Pizza pepperoni, cheddar", 30, "Large", 32, 340.00, 1, 0, 0, 0, user);
        carts.add(cart3);
        BDDMockito.when(cartService.createCartElement(cart3, user.getId())).thenReturn(carts);

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/{id}", user.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser
    public void givenNothingWhenGettingAllCartsReturnAllCArtsAndSuccessfulResponse() throws Exception{
        BDDMockito.when(cartService.getAllCartElements()).thenReturn(carts);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(cart1, cart2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
    @Test
    @WithMockUser
    public void givenUserIdWhenGettingAllCartElementsByUserIdReturnAllCartsByUser() throws Exception{
        int id = 1;
        List<Cart> userCarts = new ArrayList<>();
        for (Cart cart: carts
             ) {
            if (cart.getUser().getId() == id) userCarts.add(cart);
        }
        BDDMockito.when(cartService.getAllPizzasByUserId(id)).thenReturn(userCarts);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(cart1, cart2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenUserIdWhenDeletingCartElementReturnSuccessfulResponse() throws Exception{
        int id = 1;
        Cart cartFind = null;
        for (Cart cart: carts
             ) {
            if (cart.getId() == id) cartFind = cart;
        }
        assert cartFind != null;
        Mockito.doNothing().when(cartService).deleteCartElement(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/cart/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
