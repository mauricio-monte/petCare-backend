package com.petcare.backend.repository;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExamFileRepository  extends JpaRepository<ExamFile, Long> {
    @Query
    @Transactional
    List<ExamFile> findAllByExam(Exam exam);
}
