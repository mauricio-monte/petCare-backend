package com.petcare.backend.dto.user;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
}
