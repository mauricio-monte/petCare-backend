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

    @Column(nullable = false)
    private String description;

    @Column
    private String veterinaryClinic;

    @Column
    private boolean singleDose;

    @OneToMany
    private List<Dose> doses;

    public Vaccine(VaccineDTO vaccineDTO) {
        this.description = vaccineDTO.getDescription();
        this.veterinaryClinic = vaccineDTO.getVeterinaryClinic();
        this.singleDose = vaccineDTO.isSingleDose();
    }

}
