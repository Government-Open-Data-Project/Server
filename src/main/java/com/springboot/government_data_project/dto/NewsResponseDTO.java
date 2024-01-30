package com.springboot.government_data_project.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import lombok.NoArgsConstructor;

@Data
public class NewsResponseDTO {

    @JsonProperty("nbzyjjyoamdqqjorw")
    private List<DataDTO> dataList;

    @JsonProperty("RESULT")
    private ResultDTO result;

}
