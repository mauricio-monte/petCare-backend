package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.dto.DoseDTO;
import com.petcare.backend.repository.DoseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoseService {

    private final DoseRepository doseRepository;

    public Dose addNewDose(DoseDTO doseDTO) {
        return doseRepository.save(new Dose(doseDTO));
    }

    public Dose updateDose(Dose dose) {
        return this.doseRepository.save(dose);
    }

    public List<Dose> getDoses(Long vaccineId) {
        if (vaccineId != null) {
            return doseRepository.findAllByVaccineId(vaccineId);
        } else {
            return doseRepository.findAll();
        }
    }
}
