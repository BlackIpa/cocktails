package com.cocktails.cocktail.service;

import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.dto.UserUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.nio.file.AccessDeniedException;

public interface UserService extends UserDetailsService {

    void deleteUser(Long id, String email) throws AccessDeniedException;

    UserResponse getUserDetails(String email);

    UserResponse registerUser(SignUpRequest request);

    UserResponse updateUser(String email, UserUpdateRequest request);

}
