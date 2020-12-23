package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Comment;

import java.util.List;

public interface ICommentService {

    List<Comment> saveComment(int userId, int pizzaId, Comment comment);
    List<Comment> getAllComments();
    Comment getComment(int id);
    boolean updateComment(int id, Comment comment);
    void deleteComment(int id);

    List<Comment> getCommentsPizzaId(int pizzaId);
}
