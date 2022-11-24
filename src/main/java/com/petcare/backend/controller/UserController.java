package com.petcare.backend.controller;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.StatusReturn;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.PostDTO;
import com.petcare.backend.service.UserService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<StatusReturn> login(@RequestBody LoginDTO loginCredentials) throws Exception {
        StatusReturn result = userService.login(loginCredentials);
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }

    @PostMapping
    public ResponseEntity<StatusReturn> createNewUser(@RequestBody PostDTO postDTO) {
        StatusReturn result = userService.addNewUser(postDTO);
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }
}
