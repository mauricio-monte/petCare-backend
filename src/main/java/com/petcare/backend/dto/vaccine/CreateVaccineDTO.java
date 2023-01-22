package com.petcare.backend.dto.vaccine;

import com.petcare.backend.dto.dose.CreateDoseFromVaccineDTO;
import com.petcare.backend.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVaccineDTO {
    @NotEmpty
    private String description;
    private String veterinaryClinic;
    private Boolean isSingleDose;
    @NotNull
    private Long petId;
    private List<CreateDoseFromVaccineDTO> doses;

    public boolean vaccineHasDose() {
        return !(this.doses == null || this.doses.isEmpty());
    }
}