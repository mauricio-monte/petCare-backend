package com.petcare.backend.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetDTO {
    private String name;
    private String profileImage;
    private Integer age;
    private Float weight;
    private String species;
    private String race;
    private String allergies;
    private Long userId;
}
