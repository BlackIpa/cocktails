package com.cocktails.cocktail.service;

import com.cocktails.cocktail.dto.JwtResponse;
import com.cocktails.cocktail.dto.RefreshTokenRequest;
import com.cocktails.cocktail.dto.SignInRequest;

public interface AuthService {

    JwtResponse authenticateUser(SignInRequest request);

    JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
