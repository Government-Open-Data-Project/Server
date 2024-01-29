package com.springboot.government_data_project.controller;

import com.springboot.government_data_project.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;
    @GetMapping()
    public ResponseEntity<String> test(){
        chatGPTService.run();
        return ResponseEntity.ok().body("Good");
    }


}
