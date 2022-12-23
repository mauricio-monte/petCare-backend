package com.petcare.backend.repository;

import com.petcare.backend.domain.Dose;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoseRepository extends JpaRepository<Dose, Long> {
    List<Dose> findAllByVaccineId(Long vaccineId);
}
