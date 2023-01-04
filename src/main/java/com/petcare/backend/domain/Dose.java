package com.petcare.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petcare.backend.dto.dose.CreateDoseFromVaccineDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "doses")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dose {
    @Id
    @SequenceGenerator(
            name = "dose_sequence",
            sequenceName = "dose_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dose_sequence"
    )
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    @Temporal(TemporalType.DATE)
    private Date date;

    private boolean applied;

    public Dose(CreateDoseFromVaccineDTO createDoseFromVaccineDTO) {
        this.date = createDoseFromVaccineDTO.getDate();
        this.applied = createDoseFromVaccineDTO.isApplied();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dose dose = (Dose) o;
        return date.equals(dose.date) && applied == dose.applied;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, applied);
    }
}
