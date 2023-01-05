package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.dose.CreateDoseDTO;
import com.petcare.backend.exception.VaccineNotFoundException;
import com.petcare.backend.repository.DoseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoseService {
    private final DoseRepository doseRepository;
    private final VaccineService vaccineService;

    public Dose addNewDose(CreateDoseDTO createDoseDTO) throws VaccineNotFoundException {
        Vaccine vaccine = vaccineService.getVaccineById(createDoseDTO.getVaccineId());
        Dose dose = new Dose(createDoseDTO);
        dose.addVaccine(vaccine);
        return doseRepository.save(dose);
    }

    public Dose updateDose(Dose dose) {
        return this.doseRepository.save(dose);
    }

    public List<Dose> getDoses() {
        return doseRepository.findAll();
    }
}
