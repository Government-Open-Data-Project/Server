package com.springboot.government_data_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MessagesListResponseDTO(
        String object,
        List<MessageResponseDTO> data,
        @JsonProperty("first_id") String firstId,
        @JsonProperty("last_id") String lastId,
        @JsonProperty("has_more") boolean hasMore) {

        // 모든 메시지의 텍스트를 출력하거나 반환하는 메서드
        public void printAllMessagesText() {
                if (data != null && !data.isEmpty()) {
                        for (MessageResponseDTO message : data) {
                                // MessageResponseDTO의 showText() 메서드 호출
                                String messageText = message.ShowText();
                                System.out.println(messageText);
                        }
                } else {
                        System.out.println("No messages available.");
                }
        }
}
