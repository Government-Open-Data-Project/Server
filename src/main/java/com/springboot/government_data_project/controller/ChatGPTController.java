package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.dto.MessageResponseDTO;
import com.springboot.government_data_project.dto.MessagesListResponseDTO;
import com.springboot.government_data_project.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assistant")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;
    @GetMapping()
    public ResponseEntity<String> test(){
        return ResponseEntity.ok().body("Good");
    }

    @GetMapping("/messages")
    public ResponseEntity<MessagesListResponseDTO> getMessages() throws Exception {
        return ResponseEntity.ok().body(chatGPTService.getMessagesTest());
    }

    @PostMapping("/messages")
    public ResponseEntity<MessagesListResponseDTO> sendMessage(@RequestBody String content) throws Exception {
        chatGPTService.sendMessageTest("user", content);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatGPTService.getMessagesTest());
    }




}
