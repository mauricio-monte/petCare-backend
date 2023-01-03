package com.petcare.backend.domain;

import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.dto.pet.UpdatePetDTO;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Column
    @NotNull
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer age;

    @Column
    private Float weight;

    @Column
    private String species;

    @Column
    private String race;

    @Column
    private String allergies;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Vaccine> vaccines;

    public Pet(CreatePetDTO createPetDTO) {
        this.userId = createPetDTO.getUserId();
        this.name = createPetDTO.getName();
        this.age = createPetDTO.getAge();
        this.weight = createPetDTO.getWeight();
        this.species = createPetDTO.getSpecies();
        this.race = createPetDTO.getRace();
        this.allergies = createPetDTO.getAllergies();
    }

    public void updatePet(UpdatePetDTO pet) {
        if (pet.getName() != null) this.name = pet.getName();
        if (pet.getAge() != null) this.age = pet.getAge();
        if (pet.getWeight() != null) this.weight = pet.getWeight();
        if (pet.getSpecies() != null) this.species = pet.getSpecies();
        if (pet.getRace() != null) this.race = pet.getRace();
        if (pet.getAllergies() != null) this.allergies = pet.getAllergies();
    }

    public void addVaccine(Vaccine vaccine) {
        this.vaccines.add(vaccine);
    }
}
