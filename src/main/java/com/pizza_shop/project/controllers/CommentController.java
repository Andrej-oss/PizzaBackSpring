package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.services.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200/api")
@Slf4j
public class CommentController {

    private ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("api/comment/{userId}/{pizzaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Comment> saveComment(@PathVariable int userId, @PathVariable int pizzaId, @RequestBody @Valid Comment comment){
         return commentService.saveComment(userId, pizzaId, comment);
    }
    @PutMapping("api/comment/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean updateComment(@PathVariable int id,
                                 @RequestBody Comment comment){
       return commentService.updateComment(id, comment);
    }
    @GetMapping("api/comment/{pizzaId}")
    public List<Comment> getCommentsByPizzaId(@PathVariable int pizzaId){
        return commentService.getCommentsPizzaId(pizzaId);
    }
    @GetMapping("api/comment/user/{userName}")
    public List<Comment> getCommentsByUserName(@PathVariable String userName){
        return commentService.getUserCommentsByUserName(userName);
    }
    @DeleteMapping("api/comment/{id}")
    public void deleteComment(@PathVariable int id){
        commentService.deleteComment(id);
    }
}
