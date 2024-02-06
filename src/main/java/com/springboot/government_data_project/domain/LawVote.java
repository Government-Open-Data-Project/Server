package com.springboot.government_data_project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LawVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bill_id")
    private Long billId; // LawResponseDTO의 bill_id에 대응

    @Column(name = "user_id")
    private String userId;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;
    // Getter와 Setter
}


