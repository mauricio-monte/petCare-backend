package com.petcare.backend.service;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.UserDTO;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.user.PostDTO;
import com.petcare.backend.exception.EmailAlreadyRegisteredException;
import com.petcare.backend.exception.LoginFailedException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
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

    public LoginReturnDTO addNewUser(PostDTO userDTO) throws EmailAlreadyRegisteredException {
        validateEmail(userDTO.email);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String passwordHash = encoder.encode(userDTO.password);

        User newUser = userRepository.save(new User(userDTO.name, userDTO.username, userDTO.email, passwordHash));

        return new LoginReturnDTO(newUser);
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
            throw new LoginFailedException();
        }
    }

    private User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    private void validateEmail(String email) throws EmailAlreadyRegisteredException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isPresent()) {
            throw new EmailAlreadyRegisteredException();
        }
    }

    public LoginReturnDTO updateUser(UserDTO userDTO) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userDTO.getId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateUser(userDTO);
            return new LoginReturnDTO(userRepository.save(user));
        } else {
            throw new UserNotFoundException();
        }
    }

    public void deleteUser(Long userId) throws UserNotFoundException {
        boolean thisUserExists = userRepository.existsById(userId);

        if (thisUserExists) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException();
        }
    }
}
