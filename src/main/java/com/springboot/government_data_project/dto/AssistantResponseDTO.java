package com.springboot.government_data_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record AssistantResponseDTO(
        String id,
        String object,
        @JsonProperty("created_at") long createdAt,
        String name,
        String description,
        String model,
        String instructions,
        List<String> tools,
        @JsonProperty("file_ids") List<String> fileIds,
        // 간단한 에러 정보를 담는 내부 record 정의
        ErrorInfo error,
        Map<String, Object> metadata) {

    // 에러 정보를 담기 위한 내부 record 정의
    public record ErrorInfo(
            String message,
            String type,
            String param,
            String code) {}
}
