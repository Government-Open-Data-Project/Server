package com.springboot.government_data_project.dto.law;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LawResponse {
    private String title;
    private String content;

}
