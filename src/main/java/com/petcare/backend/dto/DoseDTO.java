package com.petcare.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petcare.backend.domain.Dose;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoseDTO {
    private Long id;

    @JsonProperty("vaccine_id")
    private Long vaccineId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date date;
    private boolean applied;

    public DoseDTO(Dose dose) {
        this.id = dose.getId();
        this.vaccineId = dose.getVaccineId();
        this.date = dose.getDate();
        this.applied = dose.isApplied();
    }
}
