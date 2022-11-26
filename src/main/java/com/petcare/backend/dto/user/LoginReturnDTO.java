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
    public Long id;
    public String name;
    public String username;
    public String email;

    public List<Pet> pets;

    public LoginReturnDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.pets = user.getPets();
    }
}
