package com.springboot.government_data_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RunRequestDTO (
        @JsonProperty("assistant_id")
        String assistantId) {}
