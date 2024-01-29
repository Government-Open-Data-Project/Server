package com.springboot.government_data_project.service;

import com.springboot.government_data_project.config.ChatGPTConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service

public class ChatGPTService {

    private final ChatGPTConfig chatGPTConfig;

    public ChatGPTService(ChatGPTConfig chatGPTConfig){
        this.chatGPTConfig = chatGPTConfig;
    }
    
}
