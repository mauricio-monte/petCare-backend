package com.petcare.backend.controller;

import java.util.List;

import com.petcare.backend.dto.pet.UpdatePetDTO;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.service.PetService;
import com.petcare.backend.util.UrlConstants;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = UrlConstants.PET_URL)
@AllArgsConstructor
public class PetController {
    private PetService petService;
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Pet>> getPets(@RequestParam(required = false) Long userId) {
        return new ResponseEntity<>(petService.getPets(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable("id") Long petId) {
        try {
            return new ResponseEntity<>(petService.getPetById(petId), HttpStatus.OK);
        } catch (PetNotFoundException petException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petException.getMessage(), petException);
        }
    }

    @PostMapping
    public ResponseEntity<Pet> createNewPet(@RequestBody CreatePetDTO createPetDTO) {
        try {
            return new ResponseEntity<>(userService.addPetToUser(createPetDTO), HttpStatus.OK);
        } catch (UserNotFoundException petException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petException.getMessage(), petException);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable("id") Long petId, @RequestBody UpdatePetDTO pet) {
        try {
            return new ResponseEntity<>(petService.updatePet(petId, pet), HttpStatus.OK);
        } catch (PetNotFoundException petException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petException.getMessage(), petException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> deletePet(@PathVariable("id") Long petId) {
        try {
            petService.deletePet(petId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PetNotFoundException petException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petException.getMessage(), petException);
        }
    }

}
