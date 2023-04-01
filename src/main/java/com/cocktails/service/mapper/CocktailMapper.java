package com.cocktails.service.mapper;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;
import com.cocktails.dto.CocktailIngredientDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CocktailMapper {

    private final CocktailIngredientMapper cocktailIngredientMapper;

    public CocktailMapper(CocktailIngredientMapper cocktailIngredientMapper) {
        this.cocktailIngredientMapper = cocktailIngredientMapper;
    }

    public CocktailDetailsDto cocktailToDetailsDto(Cocktail cocktail) {

        CocktailDetailsDto cocktailDetailsDto = new CocktailDetailsDto();
        cocktailDetailsDto.setId(cocktail.getId());
        cocktailDetailsDto.setName(cocktail.getName());
        cocktailDetailsDto.setRecipe(cocktail.getRecipe());
        cocktailDetailsDto.setCocktailIngredientsDto(cocktail.getCocktailIngredients().stream()
                .map(cocktailIngredientMapper::cocktailIngredientToDto)
                .collect(Collectors.toList()));
        return cocktailDetailsDto;
    }
}
