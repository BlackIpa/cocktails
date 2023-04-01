package com.cocktails.dto;

import com.cocktails.domain.CocktailIngredient;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CocktailDetailsDto {

    private Long id;
    private List<CocktailIngredientDto> cocktailIngredientsDto;
    private String name;
    private String recipe;
}
