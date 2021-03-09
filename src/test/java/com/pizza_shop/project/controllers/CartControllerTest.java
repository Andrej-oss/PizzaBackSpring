package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Cart;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.CartService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

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

    private List<Cart> carts;
    private  Cart cart1;
    private  Cart cart2;
    private Cart cart3;
    private  User user;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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
    @WithMockUser
    public void givenValidCartBodyWhenInsertingCartElementReturnSuccessfulResponse() throws Exception{
        BDDMockito.when(cartService.createCartElement(any(), ArgumentMatchers.anyInt())).thenReturn(carts);
        final Cart cart3 = new Cart(3, "Pizza pepperoni, cheddar", 30, "Large", 32, 340.00, 1, 0, 0, 0, user);
        carts.add(cart3);
        mockMvc.perform(MockMvcRequestBuilders.post("/cart/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"id\": 3,\n" +
                        "    \"description\": \"Pizza pepperoni, cheddar\",\n" +
                        "    \"price\": 30,\n" +
                        "    \"size\": \"Large\",\n" +
                        "    \"amount\": 32,\n" +
                        "    \"volume\": 340.00,\n" +
                        "    \"pizzaId\": 1,\n" +
                        "    \"drinkId\": 0,\n" +
                        "    \"snackId\": 0,\n" +
                        "    \"dessertId\": 0,\n" +
                        "    \"user\": {\n" +
                                "\"id\": 3,\n" +
                                "    \"username\": \"Bob\",\n" +
                                "    \"password\": \"8fdgd79dfgdsadad\",\n" +
                                "    \"name\": \"Bill\",\n" +
                                "    \"lastName\": \"East\",\n" +
                                "    \"email\": \"east@gmail.com\",\n" +
                                "    \"city\": \"NY\",\n" +
                                "    \"address\": \"Madison\",\n" +
                                "    \"postCode\": \"23213\",\n" +
                                "    \"phone\": \"3879713\",\n" +
                                "    \"role\": \"ROLE_USER\",\n" +
                                "    \"active\": true,\n" +
                                "    \"activationCode\": null,\n" +
                                "    \"comments\": null,\n" +
                                "    \"cartList\": null,\n" +
                                "    \"avatar\": null,\n" +
                                "    \"purchases\": null\n" +
                                "}\n"  +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].id").value(3));
        carts.remove(cart3);
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
