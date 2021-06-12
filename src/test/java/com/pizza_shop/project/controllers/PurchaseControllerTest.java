package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.dto.PizzaDto;
import com.pizza_shop.project.dto.PurchasePageDto;
import com.pizza_shop.project.entity.Promotion;
import com.pizza_shop.project.entity.Purchase;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.PromotionService;
import com.pizza_shop.project.services.impl.PurchaseService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@WebMvcTest(PurchaseController.class)
@Import(SecurityConfig.class)
public class PurchaseControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private PurchaseService purchaseService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private List<Purchase> purchases;
    private Purchase purchase1;
    private Purchase purchase2;
    private User user;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init(){
        purchases = new ArrayList<Purchase>();
        user =  new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        purchase1 = new Purchase(1, "pepperoni", "pepperoni, mozzarella, pepper", 380.0, 1, 22, user, 1, 0, 0, 0, 1L);
        purchase2 = new Purchase(2, "coca cola", "coca cola 0.5", 0.5, 11, 4, user, 0, 1, 0, 0, 2L);
        purchases.add(purchase1);
        purchases.add(purchase2);
    }
    @Test
    @WithMockUser
    public void givenSizeAndPageRequestsParamsWhenGettingAllPurchasesReturnPurchasePageDto() throws Exception{
        int page = 0;
        int size = 2;
        String sort = "name";
        String type = "desc";
        final PageRequest pageRequest = PageRequest.of(page, size, type.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending());
        BDDMockito.given(purchaseService.getAllPurchases(pageRequest)).willReturn(new PurchasePageDto(purchases, purchases.size(), size, purchases.size() / size, page));
        mockMvc.perform(MockMvcRequestBuilders.get("api/purchase?page=" + page + "&size=" + size + "&type=" + type + "&sort=" + sort))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(purchases.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(purchases.size() / size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchases[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.purchases[0].name").value("pepperoni"));
    }
    @Test
    @WithMockUser
    public void givenUserIdWhenGettingAllPurchasesByUserIdReturnAllPurchasesByUser() throws Exception{
        int id = 1;
        List<Purchase> purchasesUser = new ArrayList<>();
        for (Purchase purchase: purchases
             ) {
            if (purchase.getUser().getId() == id) purchasesUser.add(purchase);
        }
            BDDMockito.when(purchaseService.getAllPurchasesByUserId(id)).thenReturn(purchasesUser);

            mockMvc.perform(MockMvcRequestBuilders.get("api/purchase/{id}", id))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(purchase1, purchase2))))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenPurchaseIdWhenDeletingPurchaseReturnSuccessfulResponse() throws Exception{
        int id =  1;
        Purchase purchaseFind = null;
        for (Purchase purchase: purchases
             ) {
            if (purchase.getId() == id) purchaseFind = purchase;
        }
        assert purchaseFind != null;
        BDDMockito.when(purchaseService.deletePurchase(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("api/purchase/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }
}
