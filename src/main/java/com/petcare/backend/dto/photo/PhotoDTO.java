package com.petcare.backend.dto.photo;

import com.petcare.backend.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {

    @NotNull
    private Long petId;
    @NotEmpty
    private String photo;
    private String description;
}
