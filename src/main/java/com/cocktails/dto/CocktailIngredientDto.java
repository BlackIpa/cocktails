package com.cocktails.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CocktailIngredientDto {

    private Long id;
    private String quantity;
    private IngredientDto ingredient;
}
