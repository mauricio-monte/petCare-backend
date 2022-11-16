package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.DoseDTO;
import com.petcare.backend.dto.VaccineDTO;
import com.petcare.backend.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final DoseService doseService;

    public List<Vaccine> getVaccines(){
        return vaccineRepository.findAll();
    }

    public Vaccine getVaccineById(Long vaccineId) {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineId);

        if (optionalVaccine.isPresent()) {
            return optionalVaccine.get();
        } else {
            throw new EntityNotFoundException("Vaccine not found");
        }

    }

    public Vaccine addNewVaccine(VaccineDTO vaccineDTO) {
        boolean thisVaccineHasADose = vaccineDTO.getDoses().size() > 0;
        List<Dose> doses = new ArrayList<>();

        if (thisVaccineHasADose) {
            for (DoseDTO dose: vaccineDTO.getDoses()) {
                Dose newDose = doseService.addNewDose(dose);
                doses.add(newDose);
            }
        }


        Vaccine vaccine = new Vaccine(vaccineDTO);
        vaccine.setDoses(doses);
        return vaccineRepository.save(vaccine);
    }


}
