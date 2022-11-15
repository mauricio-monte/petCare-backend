package com.petcare.backend.controller;

import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.VaccineDTO;
import com.petcare.backend.util.UrlConstants;
import com.petcare.backend.service.VaccineService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.VACCINE_URL)
@AllArgsConstructor
public class VaccineController {

    private VaccineService vaccineService;


    @GetMapping
    public List<Vaccine> getUsers(){
        return vaccineService.getVaccines();
    }

    @PostMapping
    public void createNewUser(@RequestBody VaccineDTO vaccineDTO) {
        vaccineService.addNewUser(vaccineDTO);
    }

}
