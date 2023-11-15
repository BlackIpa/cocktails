package com.cocktails.cocktail.repository;

import com.cocktails.cocktail.model.RefreshToken;
import com.cocktails.cocktail.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

}
