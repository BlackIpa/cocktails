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
    private final CocktailStepMapper stepMapper;

    public CocktailMapper(CocktailIngredientMapper cocktailIngredientMapper, CocktailStepMapper stepMapper) {
        this.cocktailIngredientMapper = cocktailIngredientMapper;
        this.stepMapper = stepMapper;
    }

    public CocktailDetailsDto cocktailToDetailsDto(Cocktail cocktail) {

        CocktailDetailsDto cocktailDetailsDto = new CocktailDetailsDto();
        cocktailDetailsDto.setId(cocktail.getId());
        cocktailDetailsDto.setName(cocktail.getName());
        cocktailDetailsDto.setCocktailIngredientsDto(cocktail.getCocktailIngredients().stream()
                .map(cocktailIngredientMapper::cocktailIngredientToDto)
                .collect(Collectors.toList()));
        cocktailDetailsDto.setStepsDto(cocktail.getSteps().stream()
                .map(stepMapper::stepToDto)
                .collect(Collectors.toList()));
        return cocktailDetailsDto;
    }
}
