package com.petcare.backend.service;

import com.petcare.backend.domain.Pet;
import com.petcare.backend.domain.User;
import com.petcare.backend.dto.pet.CreatePetDTO;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.dto.user.UpdateUserDTO;
import com.petcare.backend.exception.EmailAlreadyRegisteredException;
import com.petcare.backend.exception.LoginFailedException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.exception.UsernameAlreadyRegisteredException;
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
    private final PetService petService;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public LoginReturnDTO addNewUser(CreateUserDTO userDTO) throws EmailAlreadyRegisteredException, UsernameAlreadyRegisteredException {
        validateEmail(userDTO.getEmail());
        validateUsername(userDTO.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String passwordHash = encoder.encode(userDTO.getPassword());

        User newUser = userRepository.save(new User(userDTO.getName(), userDTO.getUsername(), userDTO.getEmail(), passwordHash));

        return new LoginReturnDTO(newUser);
    }

    public Pet addPetToUser(CreatePetDTO createPetDTO) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(createPetDTO.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Pet pet = this.petService.addNewPet(createPetDTO);
            user.addPet(pet);
            userRepository.save(user);
            return pet;
        } else {
            throw new UserNotFoundException();
        }
    }

    public LoginReturnDTO login(LoginDTO loginCredentials) throws Exception {
        User user = this.getUserByUsername(loginCredentials.username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);

        if (encoder.matches(loginCredentials.password, user.getPasswordHash())) {
            LoginReturnDTO userInfo = new LoginReturnDTO(user);
            return userInfo;
        } else {
            throw new LoginFailedException();
        }
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

    private void validateUsername(String username) throws UsernameAlreadyRegisteredException {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyRegisteredException();
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
}
