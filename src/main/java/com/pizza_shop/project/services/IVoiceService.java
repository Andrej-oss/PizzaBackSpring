package com.pizza_shop.project.services;

import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.entity.Voice;

import java.util.List;

public interface IVoiceService {

    List<Voice> saveVoice(int commentId, Voice voice);
    List<Voice> getAllVoices();
    Voice getVoice(int id);
    boolean updateVoice(int id, Voice voice);
    boolean deleteVoice(int id);

    List<Voice> getAllVoicesByCommentId(int commentId);
    List<Voice> getAllVoicesByUserId(int userId);
}
