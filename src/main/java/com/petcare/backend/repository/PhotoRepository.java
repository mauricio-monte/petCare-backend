package com.petcare.backend.repository;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query
    List<Photo> findAllByPet(Pet pet);
}
