package com.springboot.government_data_project.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HeadDTO {
    @JsonProperty("list_total_count")
    private  int listTotalCount;

    @JsonProperty("RESULT")
    private ResultDTO result;
}
