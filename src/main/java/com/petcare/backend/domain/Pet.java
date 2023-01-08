package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.dto.pet.UpdatePetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
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
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "birthdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "species")
    private String species;

    @Column(name = "race")
    private String race;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private List<Vaccine> vaccines;

    public Pet(CreatePetDTO createPetDTO) {
        this.name = createPetDTO.getName();
        this.birthdate = createPetDTO.getBirthdate();
        this.weight = createPetDTO.getWeight();
        this.species = createPetDTO.getSpecies();
        this.race = createPetDTO.getRace();
        this.allergies = createPetDTO.getAllergies();
    }

    public void updatePet(UpdatePetDTO pet) {
        if (pet.getName() != null) this.name = pet.getName();
        if (pet.getBirthdate() != null) this.birthdate = pet.getBirthdate();
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
