package com.petcare.backend.dto;

import com.petcare.backend.domain.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String username;
    private String email;
    private List<Pet> pets;
}
