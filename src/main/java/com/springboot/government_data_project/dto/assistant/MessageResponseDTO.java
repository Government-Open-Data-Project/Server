package com.springboot.government_data_project.dto.assistant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageResponseDTO(
        String id,
        String object,
        @JsonProperty("created_at") long createdAt,
        @JsonProperty("thread_id") String threadId,
        String role,
        List<Content> content,
        @JsonProperty("file_ids") List<String> fileIds,
        @JsonProperty("assistant_id") String assistantId,
        @JsonProperty("run_id") String runId,
        Map<String, Object> metadata) {

    public record Content(
            String type,
            Text text) {

        public record Text(
                String value,
                List<Object> annotations) {}
    }

    public String ShowText() {
        StringBuilder sb = new StringBuilder();
        for (Content c : content) {
            if (c.text() != null) {
                sb.append(c.text().value);
                sb.append(" "); // 각 텍스트 값 사이에 공백 추가
            }
        }
        return sb.toString().trim(); // 마지막 공백 제거
    }
}
