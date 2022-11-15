package com.petcare.backend.service;

import com.petcare.backend.domain.Vaccine;
import com.petcare.backend.dto.VaccineDTO;
import com.petcare.backend.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;

    public List<Vaccine> getVaccines(){
        return vaccineRepository.findAll();
    }

    public void addNewUser(VaccineDTO vaccineDTO) {
        Optional<Vaccine> vaccineOptional = vaccineRepository.findVaccineByName(vaccineDTO.getName());

        if (vaccineOptional.isPresent()) {
            throw new IllegalStateException("Erro no cadastro");
        }

        Vaccine vaccine = new Vaccine(vaccineDTO);
        vaccineRepository.save(vaccine);
    }
}
