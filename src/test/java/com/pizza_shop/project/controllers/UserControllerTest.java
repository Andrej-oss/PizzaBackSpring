package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.controllers.UserController;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.IUserService;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private IUserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenNothingWhenGettingAllUsersReturnAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        final User user1 = new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        users.add(user1);
        final User user2 = new User(2, "Port", "879dsadad", "Jack", "West", "west@gmail.com", "LA", "Madison", "212313123", "3879713",
                "ROLE_USER", true, null, null, null, null, null);
        users.add(user2);
        BDDMockito.willReturn(new ArrayList<User>());
        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
              //  .andExpect(MockMvcResultMatchers.jsonPath("$.").isArray())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(user1, user2))));
        }
}
