package com.petcare.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateUserDTO {
    private String name;
    private String email;
    private String password;
}
