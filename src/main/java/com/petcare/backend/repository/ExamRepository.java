package com.petcare.backend.repository;

import com.petcare.backend.domain.Exam;
import com.petcare.backend.domain.ExamFile;
import com.petcare.backend.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Query
    List<Exam> findAllByPet(Pet pet);
}
