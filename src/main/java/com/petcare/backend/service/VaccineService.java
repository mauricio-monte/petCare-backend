package com.petcare.backend.service;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.dose.CreateDoseFromVaccineDTO;
import com.petcare.backend.dto.vaccine.CreateVaccineDTO;
import com.petcare.backend.dto.vaccine.UpdateVaccineDTO;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.exception.VaccineNotFoundException;
import com.petcare.backend.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VaccineService {
    private final VaccineRepository vaccineRepository;
    private final PetService petService;

    public List<Vaccine> getVaccines(Long petId) throws PetNotFoundException {
        if (petId != null) {
            Pet pet = this.petService.getPetById(petId);
            return vaccineRepository.findAllByPet(pet);
        } else {
            return vaccineRepository.findAll();
        }
    }

    public Vaccine getVaccineById(Long vaccineId) throws VaccineNotFoundException {
        Optional<Vaccine> optionalVaccine = vaccineRepository.findById(vaccineId);

        if (optionalVaccine.isPresent()) {
            return optionalVaccine.get();
        } else {
            throw new VaccineNotFoundException();
        }
    }

    public Vaccine addNewVaccine(CreateVaccineDTO createVaccineDTO) throws PetNotFoundException {
        Pet pet = petService.getPetById(createVaccineDTO.getPetId());
        Vaccine vaccine = new Vaccine(createVaccineDTO);
        vaccine.addPet(pet);

        boolean thisVaccineHasADose = createVaccineDTO.vaccineHasDose();

        if (thisVaccineHasADose) {
            for (CreateDoseFromVaccineDTO doseDTO : createVaccineDTO.getDoses()) {
                vaccine.addDose(new Dose(doseDTO));
            }
        }

        return vaccineRepository.save(vaccine);
    }

    public Vaccine updateVaccine(Long vaccineId, UpdateVaccineDTO updateVaccineDTO) throws VaccineNotFoundException {
        Optional<Vaccine> vaccineOptional = vaccineRepository.findById(vaccineId);

        if (vaccineOptional.isPresent()) {
            Vaccine vaccine = vaccineOptional.get();
            vaccine.updateVaccine(updateVaccineDTO);
            vaccineRepository.save(vaccine);
            return vaccine;
        } else {
            throw new VaccineNotFoundException();
        }
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
