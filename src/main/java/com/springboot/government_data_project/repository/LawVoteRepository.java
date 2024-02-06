package com.springboot.government_data_project.repository;

import com.springboot.government_data_project.domain.LawVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LawVoteRepository extends JpaRepository<LawVote, Long> {
    Optional<LawVote> findByBillIdAndUserId(Long billId, String userId);
}

