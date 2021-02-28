package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.entity.Voice;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.UserService;
import com.pizza_shop.project.services.impl.VoiceService;
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
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VoiceController.class)
@Import(SecurityConfig.class)
public class VoiceControllerTest {

    @MockBean
    private VoiceService voiceService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationManager authenticationManager;

    private static List<Voice> voices;
    private static Comment comment;
    private static Voice voice1;
    private static Voice voice2;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    public static void init(){
        voices = new ArrayList<>();
        comment = new Comment(1, "Bob", "Pepperoni", "Very good!", 1L, null, null, null);
        voice1 =  new Voice(1, comment, 4, 1);
        voice2 =  new Voice(2, comment, 2, 1);
        voices.add(voice1);
        voices.add(voice2);
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenVoiceIdWhenDeletingVoiceByVoiceIdReturnSuccessfulResponse() throws Exception{
        int id =1;
        Voice voiceFind = null;
        for (Voice voice: voices
             ) {
            if (voice.getId() == id) voiceFind = voice;
        }
        assert voiceFind != null;
        voices.remove(voiceFind);
        BDDMockito.when(voiceService.deleteVoice(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/voice/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }
}
