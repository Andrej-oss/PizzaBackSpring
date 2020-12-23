package com.pizza_shop.project.controllers;

import com.pizza_shop.project.entity.Voice;
import com.pizza_shop.project.services.IVoiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class VoiceController {

    private IVoiceService voiceService;
    @Autowired
    public VoiceController(IVoiceService voiceService) {
        this.voiceService = voiceService;
    }

    @PostMapping("/voice/{commentId}")
    public List<Voice> saveVoice(@PathVariable int commentId, @RequestBody Voice voice){
        return voiceService.saveVoice(commentId, voice);
    }
    @DeleteMapping("/voice/{id}")
    public boolean deleteVoice(@PathVariable int id){
        return voiceService.deleteVoice(id);
    }
}
