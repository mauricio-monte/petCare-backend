package com.petcare.backend.dto.user;

import com.petcare.backend.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateUserDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

    public CreateUserDTO(String name, String email, String password) {
        Validator.validateNotEmpty(name);
        Validator.validateNotEmpty(email);
        Validator.validateNotEmpty(password);
        Validator.validateEmail(email);
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
