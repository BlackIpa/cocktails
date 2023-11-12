package com.cocktails.cocktail.service;

import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponse registerUser(SignUpRequest request);

}
