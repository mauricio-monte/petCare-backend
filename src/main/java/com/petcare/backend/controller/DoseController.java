package com.petcare.backend.controller;

import com.petcare.backend.domain.Dose;
import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.service.DoseService;
import com.petcare.backend.service.VaccineService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.DOSE_URL)
@AllArgsConstructor
public class DoseController {

    private final DoseService doseService;
    private final VaccineService vaccineService;

    @GetMapping
    public ResponseEntity<List<Dose>> getDoses() {
        return new ResponseEntity<>(doseService.getDoses(), HttpStatus.OK);
    }

}
