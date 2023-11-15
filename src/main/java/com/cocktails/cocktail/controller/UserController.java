package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.JwtResponse;
import com.cocktails.cocktail.dto.RefreshTokenRequest;
import com.cocktails.cocktail.dto.SignInRequest;
import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.service.AuthService;
import com.cocktails.cocktail.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody SignUpRequest request) {
        log.info("Register new user");
        val user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signinUser(@RequestBody SignInRequest request) {
        log.info("Signin user");
        val jwtResponse = authService.authenticateUser(request);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Refreshing access token");
        val jwtResponse = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }

}
