package com.cocktails.cocktail.dto;

import com.cocktails.cocktail.model.emuns.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupedIngredientsDto {

    private Map<IngredientType, List<IngredientDto>> ingredientsGroupedByType;

}
