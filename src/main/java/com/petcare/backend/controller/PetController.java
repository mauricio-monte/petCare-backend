package com.petcare.backend.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.dto.PetDTO;
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
    public ResponseEntity<Pet> createNewPet(@RequestBody PetDTO petDTO) {
        try {
            return new ResponseEntity<>(userService.addPetToUser(petDTO), HttpStatus.OK);
        } catch (UserNotFoundException petException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petException.getMessage(), petException);
        }
    }

    @PutMapping
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        try {
            return new ResponseEntity<>(petService.updatePet(pet), HttpStatus.OK);
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
