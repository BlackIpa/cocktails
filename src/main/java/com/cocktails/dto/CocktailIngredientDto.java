package com.cocktails.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CocktailIngredientDto {

    private Long id;
    private String quantity;
    private IngredientDto ingredient;

}
