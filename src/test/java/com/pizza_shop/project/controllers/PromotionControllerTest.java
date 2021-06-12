package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Promotion;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.PromotionService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PromotionController.class)
@Import(SecurityConfig.class)
public class PromotionControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private PromotionService promotionService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private  List<Promotion> promotions;
    private  Promotion promotion1;
    private  Promotion promotion2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public  void init(){
        promotions = new ArrayList<Promotion>();
        promotion1 = new Promotion(1, "best", new byte[]{12,12,44,56,7,8}, "/best");
        promotion2 = new Promotion(2, "bestFromTheBest", new byte[]{12,-56,44,116,7,89}, "/bestofthebest");
        promotions.add(promotion1);
        promotions.add(promotion2);
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenValidPromoBodyWhenInsertingPromotionReturnAllPromotions() throws Exception {
        Promotion promotion3 = new Promotion(3, "bestbest", new byte[]{12,-12,44,56,-7,8}, "/bestbest");
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "WX20180207-134704@2x.png",
                "image/png",
                "garlic.jpg".getBytes());
        BDDMockito.when(promotionService.savePromotion(ArgumentMatchers.any(Promotion.class), ArgumentMatchers.any(MultipartFile.class))).thenReturn(promotions);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/promotion")
                .file(image).flashAttr("promotion", promotion3))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void givenNothingWhenGettingAllPromotionsReturnAllPromotions() throws Exception{
        BDDMockito.when(promotionService.getAllPromotions()).thenReturn(promotions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/promotion"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(promotion1, promotion2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
    @Test
    public void givenPathPromotionWhenGettingImagePromotionReturnImageAndSuccessfulResponse() throws Exception{
        String path = "/bestofthebest";
        Promotion promotionFind = null;
        for (Promotion promo: promotions
             ) {
            if (promo.getPath().equals(path)) promotionFind = promo;
        }
        assert promotionFind != null;
        BDDMockito.given(promotionService.getImageByPath(path)).willReturn(promotionFind.getData());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/promotion{path}", path))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenPromotionIdWhenDeletingPromotionReturnSuccessfulResponse() throws Exception{
        int id = 1;
        Promotion promotionFind = null;
        for (Promotion promo: promotions
             ) {
            if (promo.getId() == id) promotionFind = promo;
        }
        assert promotionFind != null;
        promotions.remove(promotionFind);
        BDDMockito.doNothing().when(promotionService).deletePromotion(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/promotion/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
