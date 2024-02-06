package com.springboot.government_data_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Law {
    @Id
    @JsonProperty("BILL_NO")
    private Long billId;

    @JsonProperty("BILL_NAME")
    private String title;

    @Column(columnDefinition="LONGTEXT")
    private String content;

    @JsonProperty("LINK_URL")
    private String linkUrl;

    @Builder.Default
    private Integer likes = 0;

    @Builder.Default
    private Integer dislikes = 0;
}
