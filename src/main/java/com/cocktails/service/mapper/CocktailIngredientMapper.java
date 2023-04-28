package com.cocktails.service.mapper;

import com.cocktails.domain.CocktailIngredient;
import com.cocktails.domain.Ingredient;
import com.cocktails.dto.CocktailIngredientDto;
import com.cocktails.dto.IngredientDto;
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
