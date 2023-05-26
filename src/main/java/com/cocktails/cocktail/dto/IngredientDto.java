package com.cocktails.cocktail.dto;

import com.cocktails.cocktail.model.emuns.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {

    private Long id;
    private String name;
    private IngredientType type;

}
