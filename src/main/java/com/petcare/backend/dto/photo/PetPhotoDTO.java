package com.petcare.backend.dto.photo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
public class PetPhotoDTO {

    @NotEmpty
    private String photo;
    private String description;

}
