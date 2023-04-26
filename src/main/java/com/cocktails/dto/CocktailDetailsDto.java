package com.cocktails.dto;

import com.cocktails.domain.CocktailIngredient;
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
    private List<CocktailIngredientDto> cocktailIngredientsDto;
    private List<StepDto> stepsDto;

}
