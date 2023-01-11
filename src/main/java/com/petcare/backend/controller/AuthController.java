package com.petcare.backend.controller;

import com.petcare.backend.domain.User;
import com.petcare.backend.dto.user.LoginDTO;
import com.petcare.backend.dto.user.LoginReturnDTO;
import com.petcare.backend.exception.UserNotFoundException;
import com.petcare.backend.service.TokenService;
import com.petcare.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, UserService userService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public LoginReturnDTO login(@RequestBody LoginDTO userLogin) throws UserNotFoundException {
        var authenticationToken = new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        LOG.debug("Token requested for user: '{}'", userLogin.getEmail());
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token granted: {}", token);

        User user = userService.getUserByEmail(userLogin.getEmail());
        return new LoginReturnDTO(user, token);
    }

}