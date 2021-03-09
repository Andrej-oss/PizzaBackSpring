package com.pizza_shop.project.services;

import com.pizza_shop.project.dao.CommentDao;
import com.pizza_shop.project.dao.PizzaDao;
import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.impl.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.invocation.ArgumentMatcherAction;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentDao commentDao;
    @Mock
    private UserDao userDao;
    @Mock
    private PizzaDao pizzaDao;
    private List<Comment> comments;
    private  Comment comment1;
    private  Comment comment2;
    private  Pizza pizza;
    private User user;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public  void init(){
        comments = new ArrayList<Comment>();
        pizza = new Pizza(1, true, "/scdcsd", "papa", "description", null, 10, "1,24,6", 4, null, null, null);
        comment1 = new Comment(1, "Bob", "Pepperoni", "Very good!", 1L, null, pizza, null);
        comment2 = new Comment(2, "Joe", "Pepperoni", "Very bad", 11L, null, pizza, null);
        user =  new User(1, "Fort", "128qwsdh", "Zack", "North", "saSAA@gmail.com", "NY", "Madison", "23213", "312321213",
                "ROLE_USER", true, null, null, null, null, null);
        comments.add(comment1);
        comments.add(comment2);
    }
    @Test
    public void givenNothingWhenGettingAllCommentsReturnAllComments(){
        Mockito.when(commentDao.findAll()).thenReturn(comments);

        final List<Comment> allComments = commentService.getAllComments();
        Assertions.assertEquals(allComments.get(0).getId(), comments.get(0).getId());
    }
    @Test
    public void givenPizzaIdAndUserIdAndCommentWhenInsertingCommentReturnAllComments(){
        Comment comment3 = new Comment(3, "Bob", "Pepperoni", "Very good!", 1L, null, pizza, null);
        Mockito.when(userDao.getOne(ArgumentMatchers.anyInt())).thenReturn(user);
        Mockito.when(pizzaDao.getOne(ArgumentMatchers.anyInt())).thenReturn(pizza);
        Mockito.when(commentDao.save(comment3)).thenReturn(comment3);
        Mockito.when(commentDao.getAllCommentsByPizzaId(ArgumentMatchers.anyInt())).thenReturn(comments);
        comments.add(comment3);
        final List<Comment> actualComments = commentService.saveComment(1, 2, comment3);
        Assertions.assertEquals(actualComments.get(0).getId(), comments.get(0).getId());
        Assertions.assertEquals(actualComments.get(2).getTittle(), comments.get(2).getTittle());
    }
    @Test
    public void givenCommentIdAndNewCommentWhenPuttingCommentReturnTrue(){
        Comment newComment2 = new Comment(2, "Bob", "Pepperoni", " to bad!", 1L, null, pizza, null);
        Mockito.when(commentDao.getOne(ArgumentMatchers.anyInt())).thenReturn(comment2);
        comment2.setTittle(newComment2.getTittle());
        comment2.setBody(newComment2.getBody());
        final boolean b = commentService.updateComment(1, newComment2);
        Assertions.assertTrue(b);
    }
    @Test
    public void givenCommentIdWhenDeletingCommentByIdReturnSuccessfulResponse(){
        Mockito.when(commentDao.getOne(ArgumentMatchers.anyInt())).thenReturn(comment1);
        Mockito.doNothing().when(commentDao).delete(ArgumentMatchers.any(Comment.class));
        commentService.deleteComment(1);
        comments.remove(comment1);
        Assertions.assertEquals(comments.get(0).getId(), 2);
    }
    @Test
    public void givenPizzaIdWhenGettingAllCommentsByPizzaReturnAllCommentsByPizza(){
        Mockito.when(commentDao.getAllCommentsByPizzaId(ArgumentMatchers.anyInt())).thenReturn(comments);

        final List<Comment> commentsPizzaId = commentService.getCommentsPizzaId(ArgumentMatchers.anyInt());
        Assertions.assertEquals(commentsPizzaId.get(0).getId(), comments.get(0).getId());
    }
    @Test
    public void givenUserNAmeWhenGettingAllCommentsByUserReturnAllCommentsByUser(){
        Mockito.when(commentDao.getAllCommentsByAuthor(ArgumentMatchers.anyString())).thenReturn(comments);

        final List<Comment> userCommentsByUserName = commentService.getUserCommentsByUserName(ArgumentMatchers.anyString());

        Assertions.assertEquals(userCommentsByUserName.get(0).getId(), comments.get(0).getId());
    }
}
