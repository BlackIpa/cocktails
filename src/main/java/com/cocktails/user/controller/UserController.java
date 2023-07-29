package com.cocktails.user.controller;

import com.cocktails.user.dao.request.SignUpRequest;
import com.cocktails.user.dao.request.LogInRequest;
import com.cocktails.user.dao.response.JwtAuthenticationResponse;
import com.cocktails.user.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> register(@RequestBody SignUpRequest request) throws Exception {
        log.info("Register user: " + request.getEmail());
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody LogInRequest request) {
        log.info("Log in user: " + request.getEmail());
        return  ResponseEntity.ok(authenticationService.login(request));
    }
}
