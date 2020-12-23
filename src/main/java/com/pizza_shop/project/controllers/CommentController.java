package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.services.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class CommentController {

    private ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/comment/{userId}/{pizzaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Comment> saveComment(@PathVariable int userId, @PathVariable int pizzaId, @RequestBody Comment comment){
         return commentService.saveComment(userId, pizzaId, comment);
    }
    @PutMapping("/comment/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean updateComment(@PathVariable int id,
//                                 @PathVariable int pizzaId,
//                                 @PathVariable int userId,
                                 @RequestBody Comment comment){
       return commentService.updateComment(id, comment);
    }
    @GetMapping("/comment/{pizzaId}")
    public List<Comment> getCommentsByPizzaId(@PathVariable int pizzaId){
        return commentService.getCommentsPizzaId(pizzaId);
    }
    @DeleteMapping("/comment/{id}")
    public void deleteComment(@PathVariable int id){
        commentService.deleteComment(id);
    }
}
