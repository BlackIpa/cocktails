package com.cocktails.cocktail.service;

import com.cocktails.cocktail.model.RefreshToken;
import com.cocktails.cocktail.model.User;

public interface RefreshTokenService {

    String createRefreshToken(User user);

    boolean validateRefreshToken(String token);

    User getUserFromRefreshToken(String token);

    void deleteRefreshToken(String token);

}
