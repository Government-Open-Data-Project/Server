package com.springboot.government_data_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MessagesListResponseDTO(
        String object,
        List<MessageResponseDTO> data,
        @JsonProperty("first_id")
        String firstId,
        @JsonProperty("last_id")
        String lastId,
        @JsonProperty("has_more")

        boolean hasMore) {}
