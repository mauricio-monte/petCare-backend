package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.dto.pet.UpdatePetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pets")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Pet {
    @Id
    @SequenceGenerator(name = "pet_sequence", sequenceName = "pet_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_sequence")
    @Column(name = "id", updatable = false)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "birthdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate birthdate;

    @Column(name = "sex", nullable = false)
    private Character sex;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "species")
    private String species;

    @Column(name = "race")
    private String race;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    @EqualsAndHashCode.Exclude
    private List<Vaccine> vaccines = new ArrayList<>();

    public Pet(CreatePetDTO createPetDTO) {
        this.name = createPetDTO.getName();
        this.profileImage = createPetDTO.getProfileImage();
        this.birthdate = createPetDTO.getBirthdate();
        this.sex = createPetDTO.getSex();
        this.weight = createPetDTO.getWeight();
        this.species = createPetDTO.getSpecies();
        this.race = createPetDTO.getRace();
        this.allergies = createPetDTO.getAllergies();
    }

    public void updatePet(UpdatePetDTO pet) {
        if (pet.getName() != null) this.name = pet.getName();
        if (pet.getProfileImage() != null) this.profileImage = pet.getProfileImage();
        if (pet.getBirthdate() != null) this.birthdate = pet.getBirthdate();
        if (pet.getSex() != null) this.sex = pet.getSex();
        if (pet.getWeight() != null) this.weight = pet.getWeight();
        if (pet.getSpecies() != null) this.species = pet.getSpecies();
        if (pet.getRace() != null) this.race = pet.getRace();
        if (pet.getAllergies() != null) this.allergies = pet.getAllergies();
    }

    public void addVaccine(Vaccine vaccine) {
        this.vaccines.add(vaccine);
        vaccine.setPet(this);
    }
}
