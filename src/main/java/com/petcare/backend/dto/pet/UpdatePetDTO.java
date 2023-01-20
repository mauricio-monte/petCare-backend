package com.petcare.backend.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petcare.backend.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public UpdatePetDTO(String name, String profileImage, Date birthdate, Character sex, Float weight, String species, String race, String allergies, Long userId) {
        Validator.validateIsTodayOrBefore(birthdate);
        Validator.validateCharIsMOrF(true, sex);
        Validator.validateWeight(weight);
        this.name = name;
        this.profileImage = profileImage;
        this.birthdate = birthdate;
        this.sex = sex;
        this.weight = weight;
        this.species = species;
        this.race = race;
        this.allergies = allergies;
    }
}
