package com.springboot.government_data_project.domain;

import com.springboot.government_data_project.dto.news.RowDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class News {
    @Id @GeneratedValue
    private Long Id;

    private String compMainTitle;
    private String regDate;
    private String compContent;
    private String url;

    @Builder.Default
    private int twentiesViews = 0;
    @Builder.Default
    private int thirtiesViews = 0;
    @Builder.Default
    private int fortiesViews = 0;
    @Builder.Default
    private int fiftiesViews = 0;
    @Builder.Default
    private int sixtiesViews = 0;


    public News(String compMainTitle,  String regDate , String compContent, String url){
        this.compMainTitle = compMainTitle;
        this.regDate = regDate;
        this.compContent = compContent;
        this.url = url;

    }

    public RowDTO toRowData() {
        return new RowDTO().builder()
                .compContent(this.compContent)
                .regDate(this.regDate)
                .compMainTitle(this.compMainTitle)
                .linkUrl(this.url)
                .build();
    }
}
