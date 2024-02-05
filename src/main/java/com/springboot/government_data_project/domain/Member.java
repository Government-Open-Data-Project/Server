package com.springboot.government_data_project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String name;
    private String gender;
    private String birthYear;
    private String phoneNumber;

    private LocalDateTime createdDateTime;
    protected Member() {
    }

    public Member(String userId, String name, String gender, String birthYear, String phoneNumber){
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.phoneNumber = phoneNumber;
        this.createdDateTime = LocalDateTime.now();
    }


}
