package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.model.FavouriteCocktail;
import com.cocktails.cocktail.repository.CocktailRepository;
import com.cocktails.cocktail.repository.FavouriteCocktailRepository;
import com.cocktails.cocktail.repository.UserRepository;
import com.cocktails.cocktail.service.FavouriteCocktailService;
import com.cocktails.cocktail.service.mapper.CocktailMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteCocktailServiceImpl implements FavouriteCocktailService {

    private final CocktailMapper cocktailMapper;
    private final FavouriteCocktailRepository favouriteCocktailRepository;
    private final UserRepository userRepository;
    private final CocktailRepository cocktailRepository;

    @Override
    public String addFavouriteCocktail(String email, Long cocktailId) {
        val user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        val cocktail = cocktailRepository.findById(cocktailId).orElseThrow(
                () -> new EntityNotFoundException("Cocktail not found with id: " + cocktailId));
        val favouriteCocktail = favouriteCocktailRepository.findByUserIdAndCocktailId(user.getId(), cocktailId);
        if (favouriteCocktail.isEmpty()) {
            favouriteCocktailRepository.save(FavouriteCocktail.builder().cocktail(cocktail).user(user).build());
            return "Cocktail added to favourites";
        }
        return "Cocktail is already in favourites";
    }

    @Override
    public List<CocktailSummaryDto> getUserFavouriteCocktails(String email) {
        val userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password")).getId();
        val cocktails = favouriteCocktailRepository.findCocktailsByUserId(userId);
        return cocktails.stream()
                .map(cocktailMapper::cocktailToSummaryDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeFavouriteCocktail(String email, Long cocktailId) {
        val userId = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password")).getId();
        val favouriteCocktail = favouriteCocktailRepository.findByUserIdAndCocktailId(userId, cocktailId);
        favouriteCocktail.ifPresent(favouriteCocktailRepository::delete);
    }

}
