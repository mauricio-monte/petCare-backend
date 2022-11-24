package com.petcare.backend.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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
    public ResponseEntity<List<Pet>> getAnimals() {
        return new ResponseEntity<>(petService.getAnimals(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getAnimalById(@PathVariable("id") Long animalId) {
        try {
            return new ResponseEntity<>(petService.getAnimalById(animalId), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal not found", e);
        }
    }

    @PostMapping
    public ResponseEntity<Pet> createNewAnimal(@RequestBody PetDTO petDTO) {
        try {
            return new ResponseEntity<>(petService.addNewAnimal(petDTO), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal not found", e);
        }
    }

    @PutMapping
    public ResponseEntity<Pet> updateAnimal(@RequestBody Pet pet) {
        try {
            return new ResponseEntity<>(petService.updateAnimal(pet), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal not found", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> deleteAnimal(@PathVariable("id") Long animalId) {
        try {
            petService.deleteAnimal(animalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Animal not found", e);
        }
    }

}
