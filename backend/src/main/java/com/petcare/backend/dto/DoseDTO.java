package com.petcare.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petcare.backend.domain.Dose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoseDTO {
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private boolean applied;

    public DoseDTO(Dose dose) {
        this.id = dose.getId();
        this.date = dose.getDate();
        this.applied = dose.isApplied();
    }
}
