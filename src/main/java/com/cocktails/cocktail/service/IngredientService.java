package com.cocktails.cocktail.service;

import com.cocktails.cocktail.dto.GroupedIngredientsDto;

public interface IngredientService {

    GroupedIngredientsDto findAllIngredientsGroupedByType();

    GroupedIngredientsDto findIngredientsByNamePart(String ingredientName);

}
