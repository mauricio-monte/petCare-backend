package com.petcare.backend.controller;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.dto.user.UpdateUserDTO;
import com.petcare.backend.exception.EmailAlreadyRegisteredException;
import com.petcare.backend.exception.LoginFailedException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.exception.UsernameAlreadyRegisteredException;
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

    @PostMapping("/login")
    public ResponseEntity<LoginReturnDTO> login(@RequestBody LoginDTO loginCredentials) throws Exception {
        try {
            LoginReturnDTO result = userService.login(loginCredentials);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (LoginFailedException | UserNotFoundException loginException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, loginException.getMessage(), loginException);
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<LoginReturnDTO> createNewUser(@RequestBody CreateUserDTO postDTO) {
        try {
            return new ResponseEntity<>(userService.addNewUser(postDTO), HttpStatus.CREATED);
        } catch (EmailAlreadyRegisteredException | UsernameAlreadyRegisteredException conflictException) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, conflictException.getMessage(), conflictException);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LoginReturnDTO> updateUser(@PathVariable("id") Long userId, @RequestBody UpdateUserDTO userDTO) {
        try {
            return new ResponseEntity<>(userService.updateUser(userId, userDTO), HttpStatus.OK);
        } catch (UserNotFoundException updateException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, updateException.getMessage(), updateException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException deleteException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, deleteException.getMessage(), deleteException);
        }
    }
}
