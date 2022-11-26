package com.petcare.backend.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
    private int age;

    @Column
    private float weight;

    @Column
    private String species;

    @Column
    private String race;

    @Column
    private String allergies;

    @OneToMany(orphanRemoval = true)
    private List<Vaccine> vaccines;

    public Pet(PetDTO petDTO) {
        this.name = petDTO.getName();
        this.age = petDTO.getAge();
        this.weight = petDTO.getWeight();
        this.species = petDTO.getSpecies();
        this.race = petDTO.getRace();
        this.allergies = petDTO.getAllergies();
    }

}
