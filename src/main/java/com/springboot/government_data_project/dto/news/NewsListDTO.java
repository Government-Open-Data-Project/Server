package com.springboot.government_data_project.dto.news;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsListDTO {
    private List<RowDTO> newsList = new ArrayList<>();

    public void addNews(List<RowDTO> rowDTO){
        this.newsList.addAll(rowDTO);
    }
}
