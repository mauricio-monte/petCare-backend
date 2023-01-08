package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.petcare.backend.dto.dose.CreateDoseDTO;
import com.petcare.backend.dto.dose.CreateDoseFromVaccineDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "doses")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dose {
    @Id
    @SequenceGenerator(name = "dose_sequence", sequenceName = "dose_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dose_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "is_applied", nullable = false)
    private boolean isApplied = false;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "vaccine_id", referencedColumnName = "id")
    private Vaccine vaccine;

    public Dose(CreateDoseDTO createDoseDTO) {
        this.date = createDoseDTO.getDate();
        this.isApplied = createDoseDTO.getIsApplied();
    }

    public Dose(CreateDoseFromVaccineDTO createDoseDTO) {
        this.date = createDoseDTO.getDate();
        this.isApplied = createDoseDTO.getIsApplied();
    }
}
