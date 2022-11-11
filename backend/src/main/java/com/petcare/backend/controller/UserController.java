package com.petcare.backend.controller;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.dto.user.PostDTO;
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

    @GetMapping("/login")
    public LoginReturnDTO login(@RequestBody LoginDTO loginCredentials) throws Exception {
        return userService.login(loginCredentials);
    }

    @PostMapping
    public void createNewUser(@RequestBody PostDTO postDTO) {
        userService.addNewUser(postDTO);
    }

}
