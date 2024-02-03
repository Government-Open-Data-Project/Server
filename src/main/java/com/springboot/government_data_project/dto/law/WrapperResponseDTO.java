package com.springboot.government_data_project.dto.law;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WrapperResponseDTO {
    @JsonProperty("TVBPMBILL11")
    private List<LawListResponseDTO> tvbpmbill11;
}
