package service;

import config.ChatGPTConfig;
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
