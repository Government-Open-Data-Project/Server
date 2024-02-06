package com.springboot.government_data_project.dto.law;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityLawDataDTO {
    @JsonProperty("BILL_NAME") // API 응답에서의 BILL_NAME을 title 필드에 매핑
    private String title;

    private String content;

    @JsonProperty("LINK_URL") // API 응답에서의 LINK_URL을 linkUrl 필드에 매핑
    private String linkUrl;

    @Builder.Default
    private Integer like = 0;
    @Builder.Default
    private Integer unLike = 0;
}
