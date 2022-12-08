package com.petcare.backend.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.petcare.backend.exception.PetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping
    public ResponseEntity<List<Pet>> getPets() {
        return new ResponseEntity<>(petService.getPets(), HttpStatus.OK);
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
            return new ResponseEntity<>(petService.addNewPet(petDTO), HttpStatus.OK);
        } catch (EntityNotFoundException petException) {
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
