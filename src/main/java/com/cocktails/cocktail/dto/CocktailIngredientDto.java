package com.cocktails.cocktail.dto;

import com.cocktails.cocktail.model.emuns.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CocktailIngredientDto {

    private Long id;
    private BigDecimal quantity;
    private Unit unit;
    private IngredientDto ingredient;

}
