package com.petcare.backend.repository;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.domain.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoseRepository extends JpaRepository<Dose, Long> {
    List<Dose> findAllByVaccine(Vaccine vaccine);
}
