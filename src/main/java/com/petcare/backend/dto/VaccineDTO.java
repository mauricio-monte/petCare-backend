package com.petcare.backend.dto;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.domain.Vaccine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccineDTO {
    private Long id;
    private String description;
    private String veterinaryClinic;
    private boolean singleDose;
    private List<DoseDTO> doses;

    public VaccineDTO(Vaccine vaccine) {
        this.id = vaccine.getId();
        this.description = vaccine.getDescription();
        this.veterinaryClinic = vaccine.getVeterinaryClinic();
        this.singleDose = vaccine.isSingleDose();

        List<DoseDTO> doses = new ArrayList<>();

        for (Dose dose: vaccine.getDoses()) {
            DoseDTO doseDTO = new DoseDTO(dose);
            doses.add(doseDTO);
        }

        this.doses = doses;
    }
}
