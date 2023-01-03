package com.petcare.backend.dto.pet;

import com.petcare.backend.domain.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetDTO {
    private String name;
    private Integer age;
    private Float weight;
    private String species;
    private String race;
    private String allergies;
    private Long userId;

    public CreatePetDTO(Pet pet) {
        this.name = pet.getName();
        this.age = pet.getAge();
        this.weight = pet.getWeight();
        this.species = pet.getSpecies();
        this.race = pet.getRace();
        this.allergies = pet.getAllergies();
    }
}
