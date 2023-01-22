package com.petcare.backend.controller;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.user.CreateUserDTO;
import com.petcare.backend.dto.user.UpdateUserDTO;
import com.petcare.backend.exception.EmailAlreadyRegisteredException;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.exception.UsernameAlreadyRegisteredException;
import com.petcare.backend.service.UserService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @PostMapping
    public ResponseEntity<User> createNewUser(@Valid @RequestBody CreateUserDTO postDTO) {
        try {
            return new ResponseEntity<>(userService.addNewUser(postDTO), HttpStatus.CREATED);
        } catch (EmailAlreadyRegisteredException | UsernameAlreadyRegisteredException conflictException) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, conflictException.getMessage(), conflictException);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        try {
            return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, userNotFoundException.getMessage(), userNotFoundException);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@NotNull @PathVariable("id") Long userId, @Valid @RequestBody UpdateUserDTO userDTO) {
        try {
            return new ResponseEntity<>(userService.updateUser(userId, userDTO), HttpStatus.OK);
        } catch (UserNotFoundException updateException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, updateException.getMessage(), updateException);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@NotNull @PathVariable("id") Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotFoundException deleteException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, deleteException.getMessage(), deleteException);
        }
    }
}
