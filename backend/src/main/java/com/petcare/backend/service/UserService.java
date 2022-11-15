package com.petcare.backend.service;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.StatusReturn;
import com.petcare.backend.dto.UserCreationReturnDTO;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.user.PostDTO;
import com.petcare.backend.exception.EmailAlreadyRegisteredException;
import com.petcare.backend.exception.LoginFailedException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.repository.UserRepository;
import com.petcare.backend.util.StatusConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public UserCreationReturnDTO addNewUser(PostDTO userDTO) {

        try {
            validateEmail(userDTO.email);
        } catch (EmailAlreadyRegisteredException e) {
            return new UserCreationReturnDTO(new StatusReturn(e.getMessage(), HttpStatus.CONFLICT.value()));
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        String passwordHash = encoder.encode(userDTO.password);
        User newUser = new User(userDTO.name, userDTO.username, userDTO.email, passwordHash);
        userRepository.save(newUser);

        UserCreationReturnDTO result = new UserCreationReturnDTO(newUser);
        result.setStatusCode(HttpStatus.CREATED.value());
        result.setStatus(StatusConstants.USER_CREATED);
        return result;
    }

    public LoginReturnDTO login(LoginDTO loginCredentials) throws Exception {
        try {
            return getLogin(loginCredentials);
        } catch (LoginFailedException e) {
            return new LoginReturnDTO(new StatusReturn(e.getMessage(), HttpStatus.FORBIDDEN.value()));
        } catch (UserNotFoundException e) {
            return new LoginReturnDTO(new StatusReturn(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        }
    }

    private LoginReturnDTO getLogin(LoginDTO loginCredentials) throws LoginFailedException, UserNotFoundException {
        User user = this.getUserByUsername(loginCredentials.username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);

        if (encoder.matches(loginCredentials.password, user.getPasswordHash())) {
            LoginReturnDTO userInfo = new LoginReturnDTO();
            userInfo.id = user.getId();
            userInfo.name = user.getName();
            userInfo.username = user.getUsername();
            userInfo.email = user.getEmail();
            userInfo.setStatus(StatusConstants.LOGIN_SUCCESS);
            userInfo.setStatusCode(HttpStatus.OK.value());
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
}
