package com.petcare.backend.service;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.user.PostDTO;
import com.petcare.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void addNewUser(PostDTO userDTO) {
        Optional<User> userOptional = userRepository.findUserByEmail(userDTO.email);

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Erro no cadastro");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String passwordHash = encoder.encode(userDTO.password);
        User newUser = new User(userDTO.name, userDTO.username, userDTO.email, passwordHash);
        userRepository.save(newUser);
    }

    public LoginReturnDTO login(LoginDTO loginCredentials) throws Exception {
        User user = this.getUserByUsername(loginCredentials.username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);

        if (encoder.matches(loginCredentials.password, user.getPasswordHash())) {
            LoginReturnDTO userInfo = new LoginReturnDTO();
            userInfo.id = user.getId();
            userInfo.name = user.getName();
            userInfo.username = user.getUsername();
            userInfo.email = user.getEmail();
            return userInfo;
        } else {
            throw new Exception("Login error");
        }
    }
}
