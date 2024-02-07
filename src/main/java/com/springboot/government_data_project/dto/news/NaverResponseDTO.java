package com.springboot.government_data_project.dto.news;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NaverResponseDTO {
    private String title;
    private String pubDate;
    private String description;
    private String link;

}
