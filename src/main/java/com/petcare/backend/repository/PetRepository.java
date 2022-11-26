package com.petcare.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petcare.backend.domain.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
