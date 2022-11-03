package com.petcare.backend.user;

import com.petcare.backend.user.dtos.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
       return userService.getUsers();
    }

    @PostMapping
    public void createNewUser(@RequestBody PostDTO user) {
        userService.addNewUser(user);
    }

}
