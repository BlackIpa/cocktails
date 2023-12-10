package com.cocktails.cocktail.dto;

import com.cocktails.cocktail.model.emuns.PreparationMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CocktailDetailsDto {

    private Long id;
    private String name;
    private PreparationMethod preparationMethod;
    private String glassName;
    private String glassIcon;
    private List<CocktailIngredientDto> cocktailIngredientsDto;
    private List<StepDto> stepsDto;

}
