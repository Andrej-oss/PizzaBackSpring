package com.pizza_shop.project.dao;

import com.pizza_shop.project.entity.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoiceDao extends JpaRepository<Voice, Integer> {
    List<Voice> getAllVoicesByCommentId(int commentId);

    @Query("select v from Voice v where v.userId = :userId")
    List<Voice> getAllVoicesByUserId(int userId);
}
