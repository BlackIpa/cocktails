package com.cocktails.cocktail.service;

import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.dto.UserUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponse getUserDetails(String email);

    UserResponse registerUser(SignUpRequest request);

    UserResponse updateUser(String email, UserUpdateRequest request);

}
