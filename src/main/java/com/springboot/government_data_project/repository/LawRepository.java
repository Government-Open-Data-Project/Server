package com.springboot.government_data_project.repository;

import com.springboot.government_data_project.dto.law.LawResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LawRepository extends JpaRepository<LawResponseDTO, Long> {

}
