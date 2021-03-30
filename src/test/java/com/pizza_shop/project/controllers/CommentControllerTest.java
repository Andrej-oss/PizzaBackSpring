package com.pizza_shop.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizza_shop.project.config.SecurityConfig;
import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.entity.Voice;
import com.pizza_shop.project.services.JwtService;
import com.pizza_shop.project.services.impl.CommentService;
import com.pizza_shop.project.services.impl.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CommentController.class)
@Import(SecurityConfig.class)
public class CommentControllerTest {

    @MockBean
    private CommentService commentService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthenticationManager authenticationManager;


    private  List<Comment> comments;
    private  Comment comment1;
    private  Comment comment2;
    private  Pizza pizza;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public  void init(){
        comments = new ArrayList<Comment>();
        pizza = new Pizza(1, true, "/scdcsd", "papa", "description", null, 10.00, "1,24,6", 4, null, null, null);
        comment1 = new Comment(1, "Bob", "Pepperoni", "Very good!", 1L, null, pizza, null);
        comment2 = new Comment(2, "Joe", "Pepperoni", "Very bad", 11L, null, pizza, null);
        comments.add(comment1);
        comments.add(comment2);
    }
    @Test
    @WithAnonymousUser
    public void givenUserNameWhenGettingCommentReturnComments() throws Exception{
        String name = "Joe";
        List<Comment> commentsFind = new ArrayList<Comment>();
        for (Comment comment: comments
             ) {
            if (comment.getAuthor().equals(name)) commentsFind.add(comment);
        }
        System.out.println(commentsFind);
        BDDMockito.given(commentService.getUserCommentsByUserName(name)).willReturn(commentsFind);

        mockMvc.perform(MockMvcRequestBuilders.get("/comment/user/{name}", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(comment2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2));
    }
    @Test
    public void givenPizzaIdWhenGettingCommentsByPizzaIdReturnSuccessfulResponseAndCommentsBiPizzaId() throws Exception {
        int pizzaId = 1;
        List<Comment> commentsFind = new ArrayList<Comment>();
        for (Comment comment: comments
             ) {
            if (comment.getPizza().getId() == pizzaId) commentsFind.add(comment);
        }
        BDDMockito.given(commentService.getCommentsPizzaId(pizzaId)).willReturn(commentsFind);

        mockMvc.perform(MockMvcRequestBuilders.get("/comment/{id}", pizzaId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(comment1, comment2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void givenCommentIdWhenDeletingCommentReturnSuccessfulResponse() throws Exception{
        int id = 1;
        Comment commentFind = null;
        for (Comment comment: comments
             ) {
            if (comment.getId() == id) commentFind = comment;
        }

        comments.remove(commentFind);
        Mockito.doNothing().when(commentService).deleteComment(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/comment/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithMockUser
    public void givenValidCommentBodyWhenInsertingCommentReturnAllComments() throws Exception {
        Comment comment3 = new Comment(3, "Loc", "PepperoniTest", "Very good!", 1L, null, pizza, null);
        comments.add(comment3);
        Mockito.when(commentService.saveComment(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(), any(Comment.class))).thenReturn(comments);
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\"id\": 3,\n" +
                        "    \"author\": \"Loc\",\n" +
                        "    \"tittle\": \"PepperoniTest\",\n" +
                        "    \"body\": \"Very good!\",\n" +
                        "    \"date\": 1\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Arrays.asList(comment1, comment2, comment3))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }
}
