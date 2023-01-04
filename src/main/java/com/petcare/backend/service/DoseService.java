package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.dto.dose.CreateDoseFromVaccineDTO;
import com.petcare.backend.repository.DoseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoseService {

    private final DoseRepository doseRepository;

    public Dose addNewDose(CreateDoseFromVaccineDTO createDoseFromVaccineDTO) {
        return doseRepository.save(new Dose(createDoseFromVaccineDTO));
    }

    public Dose updateDose(Dose dose) {
        return this.doseRepository.save(dose);
    }

    public List<Dose> getDoses() {
        return doseRepository.findAll();
    }
}
