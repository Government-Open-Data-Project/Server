package com.springboot.government_data_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResultDTO {
    @JsonProperty("CODE")
    private String CODE;
    @JsonProperty("MESSAGE")
    private String MESSAGE;
}
