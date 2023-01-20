package com.petcare.backend.dto.user;

import com.google.gson.annotations.SerializedName;
import com.petcare.backend.util.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@NoArgsConstructor
@Data
public class LoginDTO {
    @NotEmpty
    @Email
    @SerializedName("email")
    public String email;
    @NotEmpty
    @SerializedName("password")
    public String password;

    public LoginDTO(String email, String password) {
        Validator.validateNotEmpty(email);
        Validator.validateNotEmpty(password);
        Validator.validateEmail(email);
        this.email = email;
        this.password = password;
    }
}
