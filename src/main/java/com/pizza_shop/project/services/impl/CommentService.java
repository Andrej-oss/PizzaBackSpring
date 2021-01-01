package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.CommentDao;
import com.pizza_shop.project.dao.PizzaDao;
import com.pizza_shop.project.dao.UserDao;
import com.pizza_shop.project.dao.VoiceDao;
import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.entity.Pizza;
import com.pizza_shop.project.entity.User;
import com.pizza_shop.project.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PizzaDao pizzaDao;

    @Autowired
    private VoiceDao voiceDao;

    @Override
    public List<Comment> saveComment(int userId, int pizzaId, Comment comment) {
        final User user = userDao.getOne(userId);
        final Pizza pizza = pizzaDao.getOne(pizzaId);
        if (user != null && pizza != null) {
            comment.setAuthor(user.getName());
            comment.setUser(user);
            comment.setPizza(pizza);
            commentDao.save(comment);
            return commentDao.getAllCommentsByPizzaId(pizzaId);
        }
        return null;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentDao.findAll();
    }

    @Override
    public Comment getComment(int id) {
        return null;
    }

    @Override
    public boolean updateComment(int id, Comment comment) {
        final Comment commentFind = commentDao.getOne(id);
        if (commentFind != null) {
            commentFind.setTittle(comment.getTittle());
            commentFind.setBody(comment.getBody());
            commentDao.flush();
            return true;
        }
        return false;
    }

    @Override
    public void deleteComment(int id) {
        final Comment comment = commentDao.getOne(id);
        if (comment != null) {
            commentDao.delete(comment);
        }
    }

    @Override
    public List<Comment> getCommentsPizzaId(int pizzaId) {
        return commentDao.getAllCommentsByPizzaId(pizzaId);
    }
}


