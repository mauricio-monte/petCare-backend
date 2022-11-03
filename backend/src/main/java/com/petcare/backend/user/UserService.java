package com.petcare.backend.user;

import com.petcare.backend.user.dtos.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void addNewUser(PostDTO userDTO) {
        Optional<User> userOptional = userRepository.findUserByEmail(userDTO.email);

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Erro no cadastro");
        }

        User newUser = new User(userDTO.name, userDTO.email, "hash", "salt");
        userRepository.save(newUser);
    }
}
