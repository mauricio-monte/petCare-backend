package com.petcare.backend.controller;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.UserDTO;
import com.petcare.backend.service.UserService;
import com.petcare.backend.util.UrlConstants;
import lombok.AllArgsConstructor;
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

    @PostMapping
    public void createNewUser(@RequestBody UserDTO userDTO) {
        userService.addNewUser(userDTO);
    }

}
