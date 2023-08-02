package com.cocktails.cocktail.service;


import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.LogInRequest;
import com.cocktails.cocktail.dto.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(SignUpRequest request) throws Exception;

    JwtAuthenticationResponse login(LogInRequest request);

}
