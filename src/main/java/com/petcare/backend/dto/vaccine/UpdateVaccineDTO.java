package com.petcare.backend.dto.vaccine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVaccineDTO {
    private String description;
    private String veterinaryClinic;
    private Boolean singleDose;
}
