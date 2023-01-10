package com.petcare.backend.repository;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findAllByPet(Pet pet);
}
