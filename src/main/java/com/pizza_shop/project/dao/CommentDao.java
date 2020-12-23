package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment, Integer> {
    List<Comment> getAllCommentsByPizzaId(int pizzaId);
}
