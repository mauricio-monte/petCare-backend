package com.petcare.backend.controller;

import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.VaccineDTO;
import com.petcare.backend.service.VaccineService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.VACCINE_URL)
@AllArgsConstructor
public class VaccineController {

    private VaccineService vaccineService;


    @GetMapping
    public List<Vaccine> getVaccines(){
        return vaccineService.getVaccines();
    }

    @GetMapping("/{id}")
    public Vaccine getVaccineById(@PathVariable("id") Long vaccineId){
        try {
            return vaccineService.getVaccineById(vaccineId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vaccine not found", e);
        }
    }

    @PostMapping
    public Vaccine createNewVaccine(@RequestBody VaccineDTO vaccineDTO) {
        return vaccineService.addNewVaccine(vaccineDTO);
    }

    @PutMapping
    public Vaccine updateVaccine(@RequestBody VaccineDTO vaccineDTO) {
        return vaccineService.addNewVaccine(vaccineDTO);
    }

}
