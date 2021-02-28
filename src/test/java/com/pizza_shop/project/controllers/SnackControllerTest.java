package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Snack;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.SnackService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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
@WebMvcTest(SnackController.class)
@Import(SecurityConfig.class)
public class SnackControllerTest {

    @MockBean
    private SnackService snackService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    private static List<Snack> snacks;
    private static Snack snack1;
    private static Snack snack2;

    @BeforeAll
    public static void init(){
        snacks = new ArrayList<Snack>();
         snack1 = new Snack(1, "chipotle", "Good tasted snack with meat and cheese",
                6, "1pcs.", new byte[]{21, 0, 11, 23, 10, -1}, "/chipotle", 0);
         snack2 = new Snack(2, "tortilla", "Good tasted snack with meat and cheese",
                7, "1pcs.", new byte[]{21, 89, 11, 56, 121, 0}, "/tortilla", 11);
        snacks.add(snack1);
        snacks.add(snack2);
    }
    @Test
    @WithMockUser
    public void givenNothingWhenGettingAllSnacksReturnAllSnacks() throws Exception{
        if (snacks.size() == 1){
            snack1 = new Snack(1, "chipotle", "Good tasted snack with meat and cheese",
                    6, "1pcs.", new byte[]{21, 0, 11, 23, 10, -1}, "/chipotle", 0);
            snacks.add(0, snack1);
        }
        BDDMockito.given(snackService.getAllSnacks()).willReturn(snacks);

        mockMvc.perform(MockMvcRequestBuilders.get("/snack"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(snack1, snack2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("chipotle"));
    }
    @Test
    @WithMockUser
    public void givenPathSnackImageWhenGettingDataImageSnackReturnSuccessfulResponse() throws Exception {
        String path = "/tortilla";
        Snack snackFind = null;
        for (Snack snack: snacks
             ) {
            if (path.equals(snack.getPath())) snackFind = snack;
        }
        assert snackFind != null;
        BDDMockito.given(snackService.getImageByPath(path)).willReturn(snackFind.getData());

        mockMvc.perform(MockMvcRequestBuilders.get("/snack/" + snackFind.getPath()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenSnackIdWhenDeletingSnackBySnackIdReturnSuccessfulResponseAndAllSnacks() throws Exception {
        int id = 1;
        Snack snackFind = null;
        for (Snack snack: snacks
             ) {
            if (id == snack.getId()) snackFind = snack;
        }
        assert snackFind != null;
        snacks.remove(snackFind);
        BDDMockito.given(snackService.deleteSnack(snackFind.getId())).willReturn(snacks);

        mockMvc.perform(MockMvcRequestBuilders.delete("/snack/" + snackFind.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(snack2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2));
    }

}
