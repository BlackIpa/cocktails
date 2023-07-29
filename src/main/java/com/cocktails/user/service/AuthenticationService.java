package com.cocktails.user.service;


import com.cocktails.user.dao.request.SignUpRequest;
import com.cocktails.user.dao.request.LogInRequest;
import com.cocktails.user.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signup(SignUpRequest request) throws Exception;

    JwtAuthenticationResponse login(LogInRequest request);

}
