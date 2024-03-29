package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.dto.UserUpdateRequest;
import com.cocktails.cocktail.exception.DuplicateException;
import com.cocktails.cocktail.model.User;
import com.cocktails.cocktail.model.emuns.Role;
import com.cocktails.cocktail.repository.UserRepository;
import com.cocktails.cocktail.service.UserService;
import com.cocktails.cocktail.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    @Override
    public void deleteUser(Long id, String email) throws AccessDeniedException {
        val user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!user.getEmail().equals(email)) {
            throw new AccessDeniedException("User is not authorized to delete this account");
        }
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserResponse getUserDetails(String email) {
        val user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.userToUserResponse(user);
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

    @Override
    public UserResponse updateUser(String email, UserUpdateRequest request) {
        val user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

}
