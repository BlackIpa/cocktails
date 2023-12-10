package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.dto.CocktailDetailsDto;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.model.Cocktail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CocktailMapper {

    private final CocktailIngredientMapper cocktailIngredientMapper;
    private final CocktailStepMapper stepMapper;

    public CocktailDetailsDto cocktailToDetailsDto(final Cocktail cocktail) {
        return CocktailDetailsDto.builder()
                .id(cocktail.getId())
                .name(cocktail.getName())
                .preparationMethod(cocktail.getPreparationMethod())
                .glassName(cocktail.getGlass().getName())
                .glassIcon(cocktail.getGlass().getIconName())
                .cocktailIngredientsDto(cocktail.getCocktailIngredients().stream()
                        .map(cocktailIngredientMapper::cocktailIngredientToDto)
                        .collect(Collectors.toList()))
                .stepsDto(cocktail.getSteps().stream()
                        .map(stepMapper::stepToDto)
                        .collect(Collectors.toList()))
                .build();
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
