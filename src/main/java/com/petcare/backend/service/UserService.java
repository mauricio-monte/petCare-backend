package com.petcare.backend.service;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.dto.user.UpdateUserDTO;
import com.petcare.backend.exception.EmailAlreadyRegisteredException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.exception.UsernameAlreadyRegisteredException;
import com.petcare.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public LoginReturnDTO addNewUser(CreateUserDTO userDTO) throws EmailAlreadyRegisteredException, UsernameAlreadyRegisteredException {
        validateEmail(userDTO.getEmail());

        String passwordHash = passwordEncoder.encode(userDTO.getPassword());

        User newUser = userRepository.save(new User(userDTO.getName(), userDTO.getEmail(), passwordHash));

        return new LoginReturnDTO(newUser);
    }

    public LoginReturnDTO updateUser(Long userId, UpdateUserDTO updateUserDTO) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.updateUser(updateUserDTO);
            return new LoginReturnDTO(userRepository.save(user));
        } else {
            throw new UserNotFoundException();
        }
    }

    public void deleteUser(Long userId) throws UserNotFoundException {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException();
        }
    }

    private void validateEmail(String email) throws EmailAlreadyRegisteredException {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException();
        }
    }
}
