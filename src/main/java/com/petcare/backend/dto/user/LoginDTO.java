package com.petcare.backend.dto.user;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
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
}
