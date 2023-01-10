package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.dose.CreateDoseDTO;
import com.petcare.backend.dto.dose.UpdateDoseDTO;
import com.petcare.backend.exception.DoseNotFoundException;
import com.petcare.backend.exception.VaccineNotFoundException;
import com.petcare.backend.repository.DoseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoseService {
    private final DoseRepository doseRepository;
    private final VaccineService vaccineService;

    public List<Dose> getDoses(Long vaccineId) throws VaccineNotFoundException {
        if (vaccineId != null) {
            Vaccine vaccine = this.vaccineService.getVaccineById(vaccineId);
            return doseRepository.findAllByVaccine(vaccine);
        } else {
            return doseRepository.findAll();
        }
    }

    public Dose getDoseById(Long doseId) throws DoseNotFoundException {
        Optional<Dose> doseOptional = doseRepository.findById(doseId);

        if (doseOptional.isPresent()) {
            return doseOptional.get();
        } else {
            throw new DoseNotFoundException();
        }
    }

    public Dose addNewDose(CreateDoseDTO createDoseDTO) throws VaccineNotFoundException {
        Vaccine vaccine = vaccineService.getVaccineById(createDoseDTO.getVaccineId());
        Dose dose = new Dose(createDoseDTO);
        vaccine.addDose(dose);
        return doseRepository.save(dose);
    }

    public Dose updateDose(Long doseId, UpdateDoseDTO updateDoseDTO) throws DoseNotFoundException {
        Dose dose = this.getDoseById(doseId);
        dose.updateDose(updateDoseDTO);
        return doseRepository.save(dose);
    }

    public void deleteDose(Long doseId) throws DoseNotFoundException {
        boolean thisDoseExists = doseRepository.existsById(doseId);

        if (thisDoseExists) {
            doseRepository.deleteById(doseId);
        } else {
            throw new DoseNotFoundException();
        }
    }
}
