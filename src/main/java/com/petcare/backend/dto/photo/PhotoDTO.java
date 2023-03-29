package com.petcare.backend.dto.photo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class PhotoDTO {

    @NotNull
    private Long petId;
    @NotEmpty
    private String photo;
    private String description;

}
