package com.springboot.government_data_project.repository;

import com.springboot.government_data_project.domain.Law;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LawRepository extends JpaRepository<Law, Long> {

    Optional<Law> findByBillId(Long billId);
}
