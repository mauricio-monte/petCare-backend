package com.petcare.backend.service;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.UserDTO;
import com.petcare.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void addNewUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findUserByEmail(userDTO.email);

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Erro no cadastro");
        }

        User newUser = new User(userDTO.name, userDTO.email, "hash", "salt");
        userRepository.save(newUser);
    }
}
