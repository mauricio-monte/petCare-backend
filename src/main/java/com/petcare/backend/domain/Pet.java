package com.petcare.backend.domain;

import java.util.List;

import javax.persistence.*;

import com.petcare.backend.dto.PetDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pet {

    @Id
    @SequenceGenerator(name = "pet_sequence", sequenceName = "pet_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_sequence")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column
    private Float weight;

    @Column
    private String species;

    @Column
    private String race;

    @Column
    private String allergies;

    @OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Vaccine> vaccines;

    public Pet(PetDTO petDTO) {
        this.name = petDTO.getName();
        this.age = petDTO.getAge();
        this.weight = petDTO.getWeight();
        this.species = petDTO.getSpecies();
        this.race = petDTO.getRace();
        this.allergies = petDTO.getAllergies();
    }

    public void updatePet(Pet pet) {
        if (pet.getName() != null) this.name = pet.getName();
        if (pet.getAge() != null) this.age = pet.getAge();
        if (pet.getWeight() != null) this.weight = pet.getWeight();
        if (pet.getSpecies() != null) this.species = pet.getSpecies();
        if (pet.getRace() != null) this.race = pet.getRace();
        if (pet.getAllergies() != null) this.allergies = pet.getAllergies();
    }
}
