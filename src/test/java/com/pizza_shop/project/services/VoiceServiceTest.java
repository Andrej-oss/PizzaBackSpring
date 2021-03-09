package com.pizza_shop.project.services;

import com.pizza_shop.project.dao.CommentDao;
import com.pizza_shop.project.dao.VoiceDao;
import com.pizza_shop.project.entity.Comment;
import com.pizza_shop.project.entity.Voice;
import com.pizza_shop.project.services.impl.VoiceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VoiceServiceTest {

    @Mock
    private VoiceDao voiceDao;
    @Mock
    private CommentDao commentDao;
    @InjectMocks
    private VoiceService voiceService;

    private List<Voice> voices;
    private Comment comment;
    private Voice voice1;
    private Voice voice2;

    @BeforeEach
    public void init(){
        voices = new ArrayList<>();
        comment = new Comment(1, "Bob", "Pepperoni", "Very good!", 1L, null, null, null);
        voice1 =  new Voice(1, comment, 4, 1);
        voice2 =  new Voice(2, comment, 2, 1);
        voices.add(voice1);
        voices.add(voice2);
    }
    @Test
    public void givenCommentIdWhenInsertingVoiceReturnAllVoicesByUser(){
        Voice voice3 =  new Voice(3, null, 4, 1);
        Mockito.when(commentDao.getOne(ArgumentMatchers.anyInt())).thenReturn(comment);
        voice3.setComment(comment);
        Mockito.when(voiceDao.save(voice3)).thenReturn(voice3);
        voices.add(voice3);
        Mockito.when(voiceDao.getAllVoicesByUserId(ArgumentMatchers.anyInt())).thenReturn(voices);
        final List<Voice> allVoicesByUserId = voiceService.saveVoice(ArgumentMatchers.anyInt(), voice3);
        Assertions.assertEquals(allVoicesByUserId.get(2).getId(), voice3.getId());
    }
    @Test
    public void givenUserIdWhenGettingAllVoicesByUserReturnAllUserVoices(){
        Mockito.when(voiceDao.getAllVoicesByUserId(ArgumentMatchers.anyInt())).thenReturn(voices);

        final List<Voice> allVoicesByUserId = voiceService.getAllVoicesByUserId(ArgumentMatchers.anyInt());
        Assertions.assertEquals(allVoicesByUserId.get(0).getId(), voices.get(0).getId());
    }
    @Test
    public void givenCommentIdWhenGettingAllVoicesByCommentIdReturnAllCommentVoices(){
        Mockito.when(voiceDao.getAllVoicesByCommentId(ArgumentMatchers.anyInt())).thenReturn(voices);

        final List<Voice> allVoicesByCommentId = voiceService.getAllVoicesByCommentId(ArgumentMatchers.anyInt());
        Assertions.assertEquals(allVoicesByCommentId.get(0).getComment(), voices.get(0).getComment());
    }
    @Test
    public void givenVoiceIdWhenDeletingVoiceReturnTrue(){
        Mockito.when(voiceDao.getOne(ArgumentMatchers.anyInt())).thenReturn(voice1);
        Mockito.doNothing().when(voiceDao).delete(voice1);
        voices.remove(voice1);
        final boolean b = voiceService.deleteVoice(ArgumentMatchers.anyInt());
        Assertions.assertTrue(b);
    }
}
