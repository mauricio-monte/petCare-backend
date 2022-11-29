package com.petcare.backend.controller;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.UserDTO;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.user.PostDTO;
import com.petcare.backend.exception.EmailAlreadyRegisteredException;
import com.petcare.backend.exception.LoginFailedException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.service.UserService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = UrlConstants.USER_URL)
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/login")
    public ResponseEntity<LoginReturnDTO> login(@RequestBody LoginDTO loginCredentials) throws Exception {
        try {
            LoginReturnDTO result = userService.login(loginCredentials);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (LoginFailedException | UserNotFoundException loginException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login error", loginException);
        }

    }

    @PostMapping
    public ResponseEntity<LoginReturnDTO> createNewUser(@RequestBody PostDTO postDTO) {
        try {
            return new ResponseEntity<>(userService.addNewUser(postDTO), HttpStatus.CREATED);
        } catch (EmailAlreadyRegisteredException emailException) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error creating user", emailException);
        }
    }

    @PutMapping
    public ResponseEntity<LoginReturnDTO> updateUser(@RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating user", e);
        }
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting user", e);
        }
    }
}
