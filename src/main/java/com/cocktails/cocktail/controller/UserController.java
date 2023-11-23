package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.JwtResponse;
import com.cocktails.cocktail.dto.RefreshTokenRequest;
import com.cocktails.cocktail.dto.SignInRequest;
import com.cocktails.cocktail.dto.SignOutRequest;
import com.cocktails.cocktail.dto.SignUpRequest;
import com.cocktails.cocktail.dto.UserResponse;
import com.cocktails.cocktail.service.AuthService;
import com.cocktails.cocktail.service.RefreshTokenService;
import com.cocktails.cocktail.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody SignInRequest request) {
        log.info("Login user");
        val jwtResponse = authService.authenticateUser(request);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody SignOutRequest request) {
        log.info("Logout user");
        try {
            refreshTokenService.deleteRefreshToken(request.getRefreshToken());
            return ResponseEntity.ok("User logout successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody SignUpRequest request) {
        log.info("Register new user");
        val user = userService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Refreshing access token");
        val jwtResponse = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @GetMapping("/details")
    public ResponseEntity<UserResponse> getUserDetails(Principal principal) {
        log.info("See account details");
        val email = principal.getName();
        val user = userService.getUserDetails(email);
        return ResponseEntity.ok(user);
    }

}
