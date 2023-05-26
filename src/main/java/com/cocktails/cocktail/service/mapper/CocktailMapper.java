package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.dto.CocktailDetailsDto;
import org.springframework.stereotype.Component;

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
        cocktailDetailsDto.setPreparationMethod(cocktail.getPreparationMethod());
        cocktailDetailsDto.setGlassName(cocktail.getGlass().getName());
        cocktailDetailsDto.setGlassIcon(cocktail.getGlass().getIconName());
        cocktailDetailsDto.setCocktailIngredientsDto(cocktail.getCocktailIngredients().stream()
                .map(cocktailIngredientMapper::cocktailIngredientToDto)
                .collect(Collectors.toList()));
        cocktailDetailsDto.setStepsDto(cocktail.getSteps().stream()
                .map(stepMapper::stepToDto)
                .collect(Collectors.toList()));
        return cocktailDetailsDto;
    }
}
