package com.petcare.backend.dto.dose;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petcare.backend.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class CreateDoseFromVaccineDTO {
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date date;
    @NotNull
    private Boolean isApplied;

    public CreateDoseFromVaccineDTO(Date date, Boolean isApplied) {
        Validator.validateNotNull(date);
        Validator.validateNotNull(isApplied);
        this.date = date;
        this.isApplied = isApplied;
    }
}
