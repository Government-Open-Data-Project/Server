package com.springboot.government_data_project.dto.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RowDTO {
    @JsonProperty("COMP_MAIN_TITLE")
    private String compMainTitle;

    @JsonProperty("REG_DATE")
    private String regDate;

    @JsonProperty("COMP_CONTENT")
    private String compContent;

    @JsonProperty("LINK_URL")
    private String linkUrl;

}
