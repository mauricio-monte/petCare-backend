package com.petcare.backend.dto.dose;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.petcare.backend.domain.Dose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoseDTO {
    @JsonProperty("vaccineId")
    private Long vaccineId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date date;
    private boolean applied;

    public CreateDoseDTO(Dose dose) {
        this.date = dose.getDate();
        this.applied = dose.isApplied();
    }
}
