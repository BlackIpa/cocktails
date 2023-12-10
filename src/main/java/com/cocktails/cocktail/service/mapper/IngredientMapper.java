package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.dto.IngredientDto;
import com.cocktails.cocktail.model.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientDto ingredientToDto(Ingredient ingredient) {
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .type(ingredient.getType())
                .build();
    }

}
