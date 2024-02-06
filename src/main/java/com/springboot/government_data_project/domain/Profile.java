package com.springboot.government_data_project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // 사용자 식별자
    private String name;
    private String age;
    private String isMarried;
    private String region;
    private String position;

    @ElementCollection
    private List<String> interests; // 사용자 관심사
}
