package com.petcare.backend.dto.user;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    @Email
    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;
}
