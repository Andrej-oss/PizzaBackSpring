package com.pizza_shop.project.services.impl;

import com.pizza_shop.project.dao.CommentDao;
import com.pizza_shop.project.dao.VoiceDao;
import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.entity.Voice;
import com.pizza_shop.project.services.IVoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoiceService implements IVoiceService {

    @Autowired
    private VoiceDao voiceDao;

    @Autowired
    private CommentDao commentDao;

    @Override
    public List<Voice> getAllVoices() {
        return null;
    }

    @Override
    public Voice getVoice(int id) {
        return null;
    }

    @Override
    public boolean updateVoice(int id, Voice voice) {
        return false;
    }

    @Override
    public boolean deleteVoice(int id) {
        final Voice voice = voiceDao.getOne(id);
        voiceDao.delete(voice);
        return true;
    }

    @Override
    public List<Voice> getAllVoicesByCommentId(int commentId) {
        return voiceDao.getAllVoicesByCommentId(commentId);
    }

    @Override
    public List<Voice> saveVoice(int commentId, Voice voice) {
        final Comment comment = commentDao.getOne(commentId);
        if (comment != null){
            voice.setComment(comment);
            voiceDao.save(voice);
            
        }
        return getAllVoicesByUserId(voice.getUserId());
    }

    @Override
    public List<Voice> getAllVoicesByUserId(int userId) {
        return voiceDao.getAllVoicesByUserId(userId);
    }
}
