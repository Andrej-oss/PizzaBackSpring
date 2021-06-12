package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private List<User> users;
    private User user1;
    private User user2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init(){
        users = new ArrayList<>();
        user1 =  new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        user2 = new User(2, "Port", "879dsadad", "Jack", "West", "west@gmail.com", "LA", "Madison", "212313123", "3879713",
                "ROLE_USER", true, null, null, null, null, null);
        users.add(user1);
        users.add(user2);
    }

    @Test
    @WithMockUser
    public void givenNothingWhenGettingAllUsersReturnAllUsers() throws Exception {
        BDDMockito.given(userService.getAllUsers()).willReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get("api/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(user1, user2))));
        }
        @Test
    public void givenUserNameWhenGettingUserReturnUser() throws Exception{
            BDDMockito.given(userService.getUserByUserName("Fort")).willReturn(user1);
            mockMvc.perform(MockMvcRequestBuilders.get("api/user/authenticate/Fort"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Fort"));
        }
        @Test
    public void givenEmailWhenSendingPasswordAndLoginReturnMessage() throws Exception{
        String name = user1.getName();
        String message = "Please activate this code and go to http://localhost:8080/email/activate/4324234-4234rfewf-23423." +
                "and your login is " + name;
        BDDMockito.given(userService.sendPasswordUserByEmail(user1.getEmail())).willReturn(message);
        mockMvc.perform(MockMvcRequestBuilders.get("api/user/remind/"+user1.getEmail()))
                .andExpect(MockMvcResultMatchers.status().isOk());
        }
        @Test
        @WithMockUser
    public void givenActivationCodeWhenActivatingUserReturnMessage() throws Exception{
            String activateCode = "342quhageug3ho354ljgfaegrpo345gker54";
                    BDDMockito.given(userService.activateUser(activateCode)).willReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.get("api/activate/342quhageug3ho354ljgfaegrpo345gker54"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }
//        @Test
//        public void givenAuthRequestWhenGettingAuthenticationResponseReturnAuthenticationResponse() throws Exception{
//            final AuthRequest authRequest = new AuthRequest("Bob", "fsd32");
//            final AuthenticationResponse authenticationResponse = new AuthenticationResponse("f43245435gregijlkgfd", "ROLE_USER", "Bob");
//            BDDMockito.given(userService.).willReturn(authenticationResponse)
//                    .
//        }
    @Test
    @WithAnonymousUser
    public void givenValidUserWhenInsertNewUserReturnAllUsers() throws Exception{
        User newUser =  new User(3, "Bob", "8fdgd79dfgdsadad", "Bill", "East", "east@gmail.com", "LA", "Madison", "212313123", "3879713",
                "ROLE_USER", false, null, null, null, null, null);
        users.add(newUser);
        Mockito.when(userService.createUser(argThat(arg -> "Bob".equals(arg.getUsername())))).thenReturn(newUser);
        mockMvc.perform(MockMvcRequestBuilders.post("api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
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
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Bob"));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenUserIdWhenDeletingUserByUserIdReturnSuccessfulResponse() throws Exception {
        int id = 1;
        BDDMockito.given(userService.deleteUser(id)).willReturn(users);
        User userFind = null;
        for (User user:users
             ) {
            if (id == user.getId()) userFind = user;
        }
        assert userFind != null;
        users.remove(userFind);
        mockMvc.perform(MockMvcRequestBuilders.delete("api/user/{id}", userFind.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(user2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2));
    }
}
