package com.petcare.backend.service;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.User;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.PetDTO;
import com.petcare.backend.dto.VaccineDTO;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetService {
    private PetRepository petRepository;
    private VaccineService vaccineService;

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long petId) throws PetNotFoundException {
        Optional<Pet> optionalAnimal = petRepository.findById(petId);

        if (optionalAnimal.isPresent()) {
            return optionalAnimal.get();
        } else {
            throw new PetNotFoundException();
        }
    }

    public Pet addNewPet(PetDTO petDTO) {
        Pet pet = createPet(petDTO);
        return petRepository.save(pet);
    }

    public Pet updatePet(Pet updatedPet) throws PetNotFoundException {
        Optional<Pet> petOptional = petRepository.findById(updatedPet.getId());

        if (petOptional.isPresent()) {
            Pet pet = petOptional.get();
            pet.updatePet(updatedPet);
            petRepository.save(pet);
            return pet;
        } else {
            throw new PetNotFoundException();
        }
    }

    private List<Vaccine> addUnsavedVaccines(Pet pet) {
        List<Vaccine> updatedVaccines = new ArrayList<>();

        for (Vaccine vaccine : pet.getVaccines()) {
            if (vaccine.getId() == null) {
                Vaccine newVaccine = vaccineService.addNewVaccine(new VaccineDTO(vaccine));
                updatedVaccines.add(newVaccine);
            } else {
                updatedVaccines.add(vaccine);
            }
        }

        return updatedVaccines;
    }

    public Vaccine addVaccineToPet(VaccineDTO vaccineDTO) throws PetNotFoundException {
        Optional<Pet> petOptional = petRepository.findById(vaccineDTO.getPetId());

        if (petOptional.isPresent()) {
            Pet pet = petOptional.get();
            Vaccine vaccine = this.vaccineService.addNewVaccine(vaccineDTO);
            pet.addVaccine(vaccine);
            petRepository.save(pet);
            return vaccine;
        } else {
            throw new PetNotFoundException();
        }
    }

    public void deletePet(Long petId) throws PetNotFoundException {
        boolean thisAnimalExists = petRepository.existsById(petId);

        if (thisAnimalExists) {
            petRepository.deleteById(petId);
        } else {
            throw new PetNotFoundException();
        }
    }

    private Pet createPet(PetDTO petDTO) {
        Pet pet = new Pet(petDTO);
        petRepository.save(pet);
        return pet;
    }

}
