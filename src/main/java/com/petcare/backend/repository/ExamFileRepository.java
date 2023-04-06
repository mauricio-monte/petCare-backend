package com.petcare.backend.repository;

import com.petcare.backend.domain.ExamFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamFileRepository  extends JpaRepository<ExamFile, Long> {
}
