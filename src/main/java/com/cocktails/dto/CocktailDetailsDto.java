package com.cocktails.dto;

import com.cocktails.domain.CocktailIngredient;
import com.cocktails.domain.enums.PreparationMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
