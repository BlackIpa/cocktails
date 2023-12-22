package com.cocktails.cocktail.repository;

import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.model.FavouriteCocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteCocktailRepository extends JpaRepository<FavouriteCocktail, Long> {

    Optional<FavouriteCocktail> findByUserIdAndCocktailId(Long userId, Long cocktailId);

    @Query("SELECT fc.cocktail FROM FavouriteCocktail fc WHERE fc.user.id = :userId")
    List<Cocktail> findCocktailsByUserId(@Param("userId") Long userId);

}
