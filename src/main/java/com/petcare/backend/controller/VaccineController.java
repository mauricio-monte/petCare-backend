package com.petcare.backend.controller;

import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.VaccineDTO;
import com.petcare.backend.exception.PetNotFoundException;
import com.petcare.backend.exception.VaccineNotFoundException;
import com.petcare.backend.service.PetService;
import com.petcare.backend.service.VaccineService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.VACCINE_URL)
@AllArgsConstructor
public class VaccineController {

    private VaccineService vaccineService;
    private PetService petService;


    @GetMapping
    public ResponseEntity<List<Vaccine>> getVaccines() {
        return new ResponseEntity<>(vaccineService.getVaccines(), HttpStatus.OK);
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
    public ResponseEntity<Vaccine> createNewVaccine(@RequestBody VaccineDTO vaccineDTO) {
        try {
            return new ResponseEntity<>(petService.addVaccineToPet(vaccineDTO), HttpStatus.CREATED);
        } catch (PetNotFoundException petNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, petNotFoundException.getMessage(), petNotFoundException);
        }
    }

    @PutMapping
    public ResponseEntity<Vaccine> updateVaccine(@RequestBody Vaccine vaccine) {
        try {
            return new ResponseEntity<>(vaccineService.updateVaccine(vaccine), HttpStatus.OK);
        } catch (VaccineNotFoundException vaccineException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, vaccineException.getMessage(), vaccineException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vaccine> deleteVaccine(@PathVariable("id") Long vaccineId) {
        try {
            vaccineService.deleteVaccine(vaccineId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (VaccineNotFoundException vaccineException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, vaccineException.getMessage(), vaccineException);
        }
    }

}
