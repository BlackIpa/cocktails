package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.exception.DuplicateException;
import com.cocktails.cocktail.model.User;
import com.cocktails.cocktail.model.emuns.Role;
import com.cocktails.cocktail.repository.UserRepository;
import com.cocktails.cocktail.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserResponse registerUser(SignUpRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateException("User with this email already exists");
        }
        val user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        val savedUser = userRepository.save(user);
        return UserResponse.builder()
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .build();
    }

}
