package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.model.RefreshToken;
import com.cocktails.cocktail.model.User;
import com.cocktails.cocktail.repository.RefreshTokenRepository;
import com.cocktails.cocktail.service.RefreshTokenService;
import com.cocktails.cocktail.service.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    @Override
    public String createRefreshToken(User user) {
        val token = jwtUtils.generateRefreshToken(user);
        val expiryDate = LocalDateTime.now().plusDays(60);
        val refreshToken = RefreshToken.builder()
                .token(token)
                .expiryDate(expiryDate)
                .user(user)
                .build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshToken::getExpiryDate)
                .filter(expiryDate -> expiryDate.isAfter(LocalDateTime.now()))
                .isPresent();
    }

    @Override
    public User getUserFromRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshToken::getUser)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Refresh Token"));
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(refreshTokenRepository::delete);
    }

}
