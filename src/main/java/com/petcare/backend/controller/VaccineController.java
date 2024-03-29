package com.petcare.backend.controller;

import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.vaccine.CreateVaccineDTO;
import com.petcare.backend.dto.vaccine.UpdateVaccineDTO;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.exception.VaccineNotFoundException;
import com.petcare.backend.service.VaccineService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.VACCINE_URL)
@AllArgsConstructor
public class VaccineController {
    private VaccineService vaccineService;

    @GetMapping
    public ResponseEntity<List<Vaccine>> getVaccines(@RequestParam(required = false) Long petId) {
        try {
            return new ResponseEntity<>(vaccineService.getVaccines(petId), HttpStatus.OK);
        } catch (PetNotFoundException petNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petNotFoundException.getMessage(), petNotFoundException);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaccine> getVaccineById(@PathVariable("id") Long vaccineId) {
        try {
            return new ResponseEntity<>(vaccineService.getVaccineById(vaccineId), HttpStatus.OK);
        } catch (VaccineNotFoundException vaccineException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, vaccineException.getMessage(), vaccineException);
        }
    }

    @PostMapping
    public ResponseEntity<Vaccine> createNewVaccine(@Valid @RequestBody CreateVaccineDTO createVaccineDTO) {
        try {
            return new ResponseEntity<>(vaccineService.addNewVaccine(createVaccineDTO), HttpStatus.CREATED);
        } catch (PetNotFoundException petNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petNotFoundException.getMessage(), petNotFoundException);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vaccine> updateVaccine(@NotNull @PathVariable("id") Long vaccineId, @Valid @RequestBody UpdateVaccineDTO updateVaccineDTO) {
        try {
            return new ResponseEntity<>(vaccineService.updateVaccine(vaccineId, updateVaccineDTO), HttpStatus.OK);
        } catch (VaccineNotFoundException vaccineException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, vaccineException.getMessage(), vaccineException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vaccine> deleteVaccine(@NotNull @PathVariable("id") Long vaccineId) {
        try {
            vaccineService.deleteVaccine(vaccineId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (VaccineNotFoundException vaccineException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, vaccineException.getMessage(), vaccineException);
        }
    }
}
