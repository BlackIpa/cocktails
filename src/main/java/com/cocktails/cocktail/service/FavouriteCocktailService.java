package com.cocktails.cocktail.service;

import com.cocktails.cocktail.dto.CocktailSummaryDto;

import java.util.List;

public interface FavouriteCocktailService {

    public String addFavouriteCocktail(String email, Long cocktailId);

    public List<CocktailSummaryDto> getUserFavouriteCocktails(String email);

    public void removeFavouriteCocktail(String email, Long cocktailId);

}
