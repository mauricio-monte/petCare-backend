package com.petcare.backend.dto.photo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class UpdatePhotoDTO {

    @NotEmpty
    private String description;
}
