package com.petcare.backend.repository;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamImageRepository extends JpaRepository<ExamImage, Long> {
    @Query
    List<ExamImage> findAllByExam(Exam exam);
}
