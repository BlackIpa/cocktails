package com.cocktails.cocktail.dto;

import com.cocktails.cocktail.model.emuns.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CocktailIngredientDto {

    private Long id;
    private BigDecimal quantity;
    private Unit unit;
    private IngredientDto ingredient;

}