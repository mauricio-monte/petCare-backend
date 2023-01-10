package com.petcare.backend.service;

import com.petcare.backend.domain.SecurityUser;
import com.petcare.backend.domain.User;
import com.petcare.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.getUserByEmail(email);

        if (userOptional.isPresent()) {
            return new SecurityUser(userOptional.get()) ;
        } else {
            throw new UsernameNotFoundException("Email not found");
        }
    }

}
