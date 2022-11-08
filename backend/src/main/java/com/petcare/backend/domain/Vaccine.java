package com.petcare.backend.domain;

import com.petcare.backend.dto.VaccineDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
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
    private String name;

    @Column
    private String veterinaryName;

    public Vaccine(VaccineDTO vaccineDTO) {
        this.name = vaccineDTO.getName();
        this.veterinaryName = vaccineDTO.getVeterinaryName();
    }

}
