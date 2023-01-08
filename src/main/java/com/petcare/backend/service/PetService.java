package com.petcare.backend.service;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.User;
import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.dto.pet.UpdatePetDTO;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.repository.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PetService {
    private PetRepository petRepository;
    private UserService userService;

    public List<Pet> getPets(Long userId) throws UserNotFoundException {
        if (userId != null) {
            User user = userService.getUserById(userId);
            return petRepository.findAllByOwner(user);
        } else {
            return petRepository.findAll();
        }
    }

    public Pet getPetById(Long petId) throws PetNotFoundException {
        Optional<Pet> optionalAnimal = petRepository.findById(petId);

        if (optionalAnimal.isPresent()) {
            return optionalAnimal.get();
        } else {
            throw new PetNotFoundException();
        }
    }

    public Pet addNewPet(CreatePetDTO createPetDTO) throws UserNotFoundException {
        User user = userService.getUserById(createPetDTO.getUserId());
        Pet pet = new Pet(createPetDTO);
        user.addPet(pet);
        return petRepository.save(pet);
    }

    public Pet updatePet(Long petId, UpdatePetDTO updatedPet) throws PetNotFoundException {
        Pet pet = getPetById(petId);
        pet.updatePet(updatedPet);
        return petRepository.save(pet);
    }

    public void deletePet(Long petId) throws PetNotFoundException {
        boolean thisAnimalExists = petRepository.existsById(petId);

        if (thisAnimalExists) {
            petRepository.deleteById(petId);
        } else {
            throw new PetNotFoundException();
        }
    }
}
