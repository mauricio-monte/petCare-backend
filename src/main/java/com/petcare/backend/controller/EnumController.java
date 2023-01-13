package com.petcare.backend.controller;

import com.petcare.backend.service.EnumService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.ENUM_URL)
@AllArgsConstructor
public class EnumController {
    private final EnumService enumService;

    @GetMapping("/species")
    public ResponseEntity<List<String>> getSpecies(){
        try {
            return new ResponseEntity<>(enumService.getSpecies(), HttpStatus.OK);
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ioException.getMessage(), ioException);
        }
    }

    @GetMapping("/vaccinesDescriptions")
    public ResponseEntity<List<String>> getVaccinesDescriptions(){
        try {
            return new ResponseEntity<>(enumService.getVaccinesDescriptions(), HttpStatus.OK);
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ioException.getMessage(), ioException);
        }
    }

}
