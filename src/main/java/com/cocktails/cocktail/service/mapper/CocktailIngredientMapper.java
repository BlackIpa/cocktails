package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.dto.CocktailIngredientDto;
import com.cocktails.cocktail.model.CocktailIngredient;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CocktailIngredientMapper {

    private final IngredientMapper ingredientMapper;

    public CocktailIngredientDto cocktailIngredientToDto(CocktailIngredient cocktailIngredient) {
        val ingredientDto = ingredientMapper.ingredientToDto(cocktailIngredient.getIngredient());
        return CocktailIngredientDto.builder()
                .id(cocktailIngredient.getId())
                .quantity(cocktailIngredient.getQuantity())
                .unit(cocktailIngredient.getUnit())
                .ingredient(ingredientDto)
                .build();
    }

}
