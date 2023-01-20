package com.petcare.backend.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petcare.backend.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePetDTO {
    private String name;
    private String profileImage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date birthdate;
    private Character sex;
    private Float weight;
    private String species;
    private String race;
    private String allergies;
}
