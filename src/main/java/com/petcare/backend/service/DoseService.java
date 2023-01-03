package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.dto.dose.CreateDoseDTO;
import com.petcare.backend.repository.DoseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoseService {

    private final DoseRepository doseRepository;

    public Dose addNewDose(CreateDoseDTO createDoseDTO) {
        return doseRepository.save(new Dose(createDoseDTO));
    }

    public Dose updateDose(Dose dose) {
        return this.doseRepository.save(dose);
    }

    public List<Dose> getDoses() {
        return doseRepository.findAll();
    }
}
