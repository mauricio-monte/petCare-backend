package com.petcare.backend.service;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.PetDTO;
import com.petcare.backend.dto.VaccineDTO;
import com.petcare.backend.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

  public Pet getPetById(Long petId) {
    Optional<Pet> optionalAnimal = petRepository.findById(petId);

    if (optionalAnimal.isPresent()) {
      return optionalAnimal.get();
    } else {
      throw new EntityNotFoundException();
    }
  }

  public Pet addNewPet(PetDTO petDTO) {
    boolean thisAnimalHasAVaccine = petDTO.getVaccines().size() > 0;

    List<Vaccine> vaccines = new ArrayList<>();

    if (thisAnimalHasAVaccine) {
      for (VaccineDTO vaccine : petDTO.getVaccines()) {
        Vaccine newVaccine = vaccineService.addNewVaccine(vaccine);
        vaccines.add(newVaccine);
      }
    }

    Pet pet = new Pet(petDTO);
    pet.setVaccines(vaccines);

    return petRepository.save(pet);
  }

  public Pet updatePet(Pet updatedPet) {
    boolean thisAnimalExists = petRepository.existsById(updatedPet.getId());

    if (thisAnimalExists) {
      updatedPet.setVaccines(this.addUnsavedVaccines(updatedPet));
      return petRepository.save(updatedPet);
    } else {
      throw new EntityNotFoundException();
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

  public void deletePet(Long petId) {
    boolean thisAnimalExists = petRepository.existsById(petId);

    if (thisAnimalExists) {
      petRepository.deleteById(petId);
    } else {
      throw new EntityNotFoundException();
    }
  }

}
