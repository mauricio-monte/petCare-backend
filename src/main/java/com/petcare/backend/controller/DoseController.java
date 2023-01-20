package com.petcare.backend.controller;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.dto.dose.CreateDoseDTO;
import com.petcare.backend.dto.dose.UpdateDoseDTO;
import com.petcare.backend.exception.DoseNotFoundException;
import com.petcare.backend.exception.VaccineNotFoundException;
import com.petcare.backend.service.DoseService;
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
@RequestMapping(path = UrlConstants.DOSE_URL)
@AllArgsConstructor
public class DoseController {
    private final DoseService doseService;

    @GetMapping
    public ResponseEntity<List<Dose>> getDoses(@RequestParam(required = false) Long vaccineId) {
        try {
            return new ResponseEntity<>(doseService.getDoses(vaccineId), HttpStatus.OK);
        } catch (VaccineNotFoundException vaccineNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, vaccineNotFoundException.getMessage(), vaccineNotFoundException);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dose> getDoseById(@PathVariable("id") Long doseId) {
        try {
            return new ResponseEntity<>(doseService.getDoseById(doseId), HttpStatus.OK);
        } catch (DoseNotFoundException doseNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, doseNotFoundException.getMessage(), doseNotFoundException);
        }
    }

    @PostMapping
    public ResponseEntity<Dose> createNewDose(@Valid @RequestBody CreateDoseDTO createDoseDTO) {
        try {
            return new ResponseEntity<>(doseService.addNewDose(createDoseDTO), HttpStatus.CREATED);
        } catch (VaccineNotFoundException vaccineNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, vaccineNotFoundException.getMessage(), vaccineNotFoundException);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Dose> updateDose(@NotNull @PathVariable("id") Long doseId, @Valid @RequestBody UpdateDoseDTO updateDoseDTO) {
        try {
            return new ResponseEntity<>(doseService.updateDose(doseId, updateDoseDTO), HttpStatus.OK);
        } catch (DoseNotFoundException doseNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, doseNotFoundException.getMessage(), doseNotFoundException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Dose> deleteDose(@NotNull @PathVariable("id") Long doseId) {
        try {
            doseService.deleteDose(doseId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DoseNotFoundException doseNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, doseNotFoundException.getMessage(), doseNotFoundException);
        }
    }
}
