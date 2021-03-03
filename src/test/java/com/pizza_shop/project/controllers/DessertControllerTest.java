package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Dessert;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.DessertService;
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
import org.springframework.http.MediaType;
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
@WebMvcTest(DessertController.class)
@Import({SecurityConfig.class})
public class DessertControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private DessertService dessertService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private List<Dessert> desserts;
    private Dessert dessert1;
    private Dessert dessert2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init(){
        desserts = new ArrayList<Dessert>();
        dessert1 = new Dessert(1, "napoleon", "Sweet and delicious", new byte[]{1,0,23,43,-11}, 12, "/napoleon", "1pcs.", 11);
        dessert2 = new Dessert(2, "cookies", "Sweet and chocolates", new byte[]{11,23,49,111,121}, 7, "/cookies", "120gr.", 111);
        desserts.add(dessert1);
        desserts.add(dessert2);
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenValidDessertBodyAndImageFileWhenInsertingDessertReturnAllDesserts() throws Exception {
        Dessert dessert3 = new Dessert(3, "choco cookies", "Sweet and chocolates", new byte[]{11,-3,0,61,11}, 6, "/chococookies", "120gr.", 13);
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "WX20180207-134704@2x.png",
                "image/png",
                "chococookies.jpg".getBytes());
        desserts.add(dessert3);
        BDDMockito.when(dessertService.saveDessert(ArgumentMatchers.any(Dessert.class), ArgumentMatchers.any(MultipartFile.class))).thenReturn(desserts);

        mockMvc.perform(MockMvcRequestBuilders.post("/dessert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\n" +
                        "\"id\": 3,\n" +
                        "    \"name\": \"choco cookies\",\n" +
                        "    \"description\": \"Sweet and chocolates\",\n" +
                        "    \"price\": 6,\n" +
                        "    \"path\": \"/chococookies\",\n" +
                        "    \"volume\": \"120gr.\",\n" +
                        "    \"ordersCount\": 13,\n" +
                   "}"))
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                .flashAttr("dessert", dessert3))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/dessert").file(file))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void givenNothingWhenGettingAllDessertsReturnAllDessertsAndSuccessfulResponse() throws Exception{
        BDDMockito.when(dessertService.getAllDesserts()).thenReturn(desserts);

        mockMvc.perform(MockMvcRequestBuilders.get("/dessert"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(dessert1, dessert2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }
    @Test
    public void givenPathDessertWhenGettingImageDessertReturnImageDessertAndSuccessfulResponse() throws Exception{
        String path = "/cookies";
        Dessert dessertFind = null;
        for (Dessert dessert: desserts
             ) {
            if (dessert.getPath().equals(path)) dessertFind = dessert;
        }
        assert dessertFind != null;
        BDDMockito.when(dessertService.getImageByPath(path)).thenReturn(dessertFind.getData());

        mockMvc.perform(MockMvcRequestBuilders.get("/dessert{path}", path))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenDessertIdWhenDeletingDessertReturnSuccessfulResponseAndAllDesserts() throws Exception{
        int id = 1;
        Dessert dessertFind = null;
        for (Dessert dessert: desserts
             ) {
            if (dessert.getId() == id) dessertFind = dessert;
        }
        assert dessertFind  != null;
        desserts.remove(dessertFind);
        BDDMockito.when(dessertService.deleteDessert(id)).thenReturn(desserts);

        mockMvc.perform(MockMvcRequestBuilders.delete("/dessert/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(dessert2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("cookies"));
    }
}
