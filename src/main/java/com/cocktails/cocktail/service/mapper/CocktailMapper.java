package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.dto.CocktailDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CocktailMapper {

    private final CocktailIngredientMapper cocktailIngredientMapper;
    private final CocktailStepMapper stepMapper;

    public CocktailDetailsDto cocktailToDetailsDto(final Cocktail cocktail) {
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

    public CocktailSummaryDto cocktailToSummaryDto(final Cocktail cocktail) {
        return CocktailSummaryDto.builder()
                .id(cocktail.getId())
                .name(cocktail.getName())
                .preparationMethod(cocktail.getPreparationMethod())
                .glassIcon(cocktail.getGlass().getIconName())
                .build();
    }

}
