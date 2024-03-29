package com.petcare.backend.dto.user;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginReturnDTO {
    private Long id;
    private String name;
    private String email;
    private String token;

    private List<Pet> pets;

    public LoginReturnDTO(User user, String token) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.token = token;
        this.pets = user.getPets();
    }
}
