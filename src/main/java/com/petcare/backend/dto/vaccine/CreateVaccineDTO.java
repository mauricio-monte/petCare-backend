package com.petcare.backend.dto.vaccine;

import com.petcare.backend.dto.dose.UpdateDoseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVaccineDTO {
    private String description;
    private String veterinaryClinic;
    private Boolean singleDose;
    private Long petId;
    private List<UpdateDoseDTO> doses;

    public boolean vaccineHasDose() {
        return !(this.doses == null || this.doses.isEmpty());
    }
}
