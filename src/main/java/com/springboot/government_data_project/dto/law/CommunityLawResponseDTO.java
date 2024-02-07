package com.springboot.government_data_project.dto.law;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityLawResponseDTO {
    private Long billId;
    private String title;
    private String content;
    private String linkUrl;
    private Integer likes;
    private Integer dislikes;
    private String isChecked; // "LIKE", "DISLIKE", 또는 "NONE"
}

