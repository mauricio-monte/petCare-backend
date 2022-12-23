package com.petcare.backend.dto;

import java.util.ArrayList;
import java.util.List;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.Vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {

    private Long userId;
    private String name;
    private int age;
    private float weight;
    private String species;
    private String race;
    private String allergies;

    public PetDTO(Pet pet) {
        this.name = pet.getName();
        this.age = pet.getAge();
        this.weight = pet.getWeight();
        this.species = pet.getSpecies();
        this.race = pet.getRace();
        this.allergies = pet.getAllergies();
    }

}
