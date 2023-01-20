package com.petcare.backend.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.petcare.backend.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class CreatePetDTO {
    @NotEmpty
    private String name;
    private String profileImage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private Date birthdate;
    @NotNull
    private Character sex;
    private Float weight;
    private String species;
    private String race;
    private String allergies;
    @NotNull
    private Long userId;

    public CreatePetDTO(String name, String profileImage, Date birthdate, Character sex, Float weight, String species, String race, String allergies, Long userId) {
        Validator.validateIsTodayOrBefore(birthdate);
        Validator.validateCharIsMOrF(false, sex);
        Validator.validateWeight(weight);
        this.name = name;
        this.profileImage = profileImage;
        this.birthdate = birthdate;
        this.sex = sex;
        this.weight = weight;
        this.species = species;
        this.race = race;
        this.allergies = allergies;
        this.userId = userId;
    }
}
