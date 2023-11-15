package com.cocktails.cocktail.service;

import com.cocktails.cocktail.model.User;

public interface RefreshTokenService {

    void createRefreshToken(User user);

    boolean validateRefreshToken(String token);

    User getUserFromRefreshToken(String token);

    void deleteRefreshToken(String token);

}
