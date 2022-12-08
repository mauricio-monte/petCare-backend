package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.DoseDTO;
import com.petcare.backend.dto.VaccineDTO;
import com.petcare.backend.exception.VaccineNotFoundException;
import com.petcare.backend.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final DoseService doseService;

    public List<Vaccine> getVaccines() {
        return vaccineRepository.findAll();
    }

    public Vaccine getVaccineById(Long vaccineId) throws VaccineNotFoundException {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineId);

        if (optionalVaccine.isPresent()) {
            return optionalVaccine.get();
        } else {
            throw new VaccineNotFoundException();
        }

    }

    public Vaccine addNewVaccine(VaccineDTO vaccineDTO) {
        boolean thisVaccineHasADose = vaccineDTO.getDoses().size() > 0;
        List<Dose> doses = new ArrayList<>();

        if (thisVaccineHasADose) {
            for (DoseDTO dose : vaccineDTO.getDoses()) {
                Dose newDose = doseService.addNewDose(dose);
                doses.add(newDose);
            }
        }
        Vaccine vaccine = new Vaccine(vaccineDTO);
        vaccine.setDoses(doses);
        return vaccineRepository.save(vaccine);
    }

    public Vaccine updateVaccine(Vaccine updatedVaccine) throws VaccineNotFoundException {
        Optional<Vaccine> vaccineOptional = vaccineRepository.findById(updatedVaccine.getId());

        if (vaccineOptional.isPresent()) {
            Vaccine vaccine = vaccineOptional.get();
            vaccine.updateVaccine(updatedVaccine);
//            List<Dose> updatedDoses = updateDoses(updatedVaccine);
            List<Dose> updatedDoses = updatedVaccine.getDoses();
            vaccine.updateDoses(updatedDoses);
            vaccineRepository.save(vaccine);

            return vaccine;
        } else {
            throw new VaccineNotFoundException();
        }
    }

//    private List<Dose> updateDoses(Vaccine updatedVaccine) {
//        List<Dose> doses = new ArrayList<>();
//        if (updatedVaccine.getDoses().size() > 0) {
//            for (Dose dose : updatedVaccine.getDoses()) {
//                Dose newDose = doseService.updateDose(dose);
//                doses.add(newDose);
//            }
//        } return doses;
//    }

    private List<Dose> addUnsavedDoses(Vaccine vaccine) {
        List<Dose> updatedDoses = new ArrayList<>();

        for (Dose dose : vaccine.getDoses()) {
            if (dose.getId() == null) {
                Dose newDose = doseService.addNewDose(new DoseDTO(dose));
                updatedDoses.add(newDose);
            } else {
                updatedDoses.add(dose);
            }
        }

        return updatedDoses;
    }

    public void deleteVaccine(Long vaccineId) throws VaccineNotFoundException {
        boolean thisVaccineExists = vaccineRepository.existsById(vaccineId);

        if (thisVaccineExists) {
            vaccineRepository.deleteById(vaccineId);
        } else {
            throw new VaccineNotFoundException();
        }
    }
}
