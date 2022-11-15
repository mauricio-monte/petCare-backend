package com.petcare.backend.dto.user;

import com.petcare.backend.dto.StatusReturn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    public String username;
    public String password;
}
