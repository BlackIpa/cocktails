package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.JwtResponse;
import com.cocktails.cocktail.dto.RefreshTokenRequest;
import com.cocktails.cocktail.dto.SignInRequest;
import com.cocktails.cocktail.repository.UserRepository;
import com.cocktails.cocktail.service.AuthService;
import com.cocktails.cocktail.service.RefreshTokenService;
import com.cocktails.cocktail.service.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    private final RefreshTokenService refreshTokenService;

    @Override
    public JwtResponse authenticateUser(SignInRequest request) {
        val authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        val user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        val jwt = jwtUtils.generateToken(user);
        val refreshToken = jwtUtils.generateRefreshToken(user);
        refreshTokenService.createRefreshToken(user);
        return JwtResponse.builder()
                .token(jwt)
                .email(user.getEmail())
                .build();
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        val refreshToken = refreshTokenRequest.getRefreshToken();
        if (!refreshTokenService.validateRefreshToken(refreshToken)) {
            throw new IllegalStateException("Invalid or expired refresh token");
        }
        val user = refreshTokenService.getUserFromRefreshToken(refreshToken);
        val newAccessToken = jwtUtils.generateToken(user);
        val newRefreshToken = jwtUtils.generateRefreshToken(user);
        refreshTokenService.deleteRefreshToken(refreshToken);
        refreshTokenService.createRefreshToken(user);
        return JwtResponse.builder()
                .token(newAccessToken)
                .email(user.getEmail())
                .refreshToken(newRefreshToken)
                .build();
    }


}
