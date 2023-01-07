package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.petcare.backend.dto.vaccine.CreateVaccineDTO;
import com.petcare.backend.dto.vaccine.UpdateVaccineDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vaccines")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vaccine {
    @Id
    @SequenceGenerator(name = "vaccine_sequence", sequenceName = "vaccine_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vaccine_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "veterinary_clinic")
    private String veterinaryClinic;

    @Column(name = "is_single_dose", nullable = false)
    private Boolean isSingleDose = false;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vaccine")
    private List<Dose> doses = new ArrayList<>();

    public Vaccine(CreateVaccineDTO vaccineDTO) {
        this.description = vaccineDTO.getDescription();
        this.veterinaryClinic = vaccineDTO.getVeterinaryClinic();
        this.isSingleDose = vaccineDTO.getIsSingleDose();
    }

    public void updateVaccine(UpdateVaccineDTO updatedVaccine) {
        if (updatedVaccine.getDescription() != null) this.description = updatedVaccine.getDescription();
        if (updatedVaccine.getVeterinaryClinic() != null) this.veterinaryClinic = updatedVaccine.getVeterinaryClinic();
        if (updatedVaccine.getIsSingleDose() != null) this.isSingleDose = updatedVaccine.getIsSingleDose();
    }

    public void addPet(Pet pet) {
        this.pet = pet;
    }

    public void addDose(Dose dose) {
        this.doses.add(dose);
        dose.addVaccine(this);
    }
}
