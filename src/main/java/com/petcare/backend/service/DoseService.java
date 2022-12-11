package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.dto.DoseDTO;
import com.petcare.backend.repository.DoseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
