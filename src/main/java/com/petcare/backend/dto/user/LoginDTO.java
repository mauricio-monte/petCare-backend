package com.petcare.backend.dto.user;

import com.google.gson.annotations.SerializedName;
import com.petcare.backend.dto.StatusReturn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
}
