package com.petcare.backend.domain;

import com.petcare.backend.dto.VaccineDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vaccines")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Vaccine {

    @Id
    @SequenceGenerator(
            name = "vaccine_sequence",
            sequenceName = "vaccine_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vaccine_sequence"
    )
    private Long id;

    @Column
    private Long petId;
    
    @Column(nullable = false)
    private String description;

    @Column
    private String veterinaryClinic;

    @Column
    private Boolean singleDose;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Dose> doses;

    public Vaccine(VaccineDTO vaccineDTO) {
        this.petId = vaccineDTO.getPetId();
        this.description = vaccineDTO.getDescription();
        this.veterinaryClinic = vaccineDTO.getVeterinaryClinic();
        this.singleDose = vaccineDTO.getSingleDose();
    }

    public void updateVaccine(Vaccine updatedVaccine) {
        if (updatedVaccine.getDescription() != null) this.description = updatedVaccine.getDescription();
        if (updatedVaccine.getVeterinaryClinic() != null) this.veterinaryClinic = updatedVaccine.getVeterinaryClinic();
        if (updatedVaccine.getSingleDose() != null) this.singleDose = updatedVaccine.getSingleDose();
    }

    public void updateDoses(List<Dose> updatedDoses) {
        if (!this.doses.equals(updatedDoses)) {
            this.doses.clear();
            this.doses.addAll(updatedDoses);
        }
    }

}
