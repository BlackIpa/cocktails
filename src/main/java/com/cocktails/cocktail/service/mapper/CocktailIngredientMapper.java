package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.model.CocktailIngredient;
import com.cocktails.cocktail.model.Ingredient;
import com.cocktails.cocktail.dto.CocktailIngredientDto;
import com.cocktails.cocktail.dto.IngredientDto;
import org.springframework.stereotype.Component;

@Component
public class CocktailIngredientMapper {

    public CocktailIngredientDto cocktailIngredientToDto(CocktailIngredient cocktailIngredient) {
        CocktailIngredientDto cocktailIngredientDto = new CocktailIngredientDto();
        cocktailIngredientDto.setId(cocktailIngredient.getId());
        cocktailIngredientDto.setQuantity(cocktailIngredient.getQuantity());
        cocktailIngredientDto.setUnit(cocktailIngredient.getUnit());

        Ingredient ingredient = cocktailIngredient.getIngredient();
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setId(ingredient.getId());
        ingredientDto.setName(ingredient.getName());
        ingredientDto.setType(ingredient.getType());
        cocktailIngredientDto.setIngredient(ingredientDto);
        return cocktailIngredientDto;
    }

}
