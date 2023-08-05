package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.LogInRequest;
import com.cocktails.cocktail.dto.JwtAuthenticationResponse;
import com.cocktails.cocktail.model.User;
import com.cocktails.cocktail.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody SignUpRequest request) {
        log.info("Register user: " + request.getEmail());
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LogInRequest request) {
        log.info("Log in user: " + request.getEmail());
        return  ResponseEntity.ok(authenticationService.login(request));
    }
}
