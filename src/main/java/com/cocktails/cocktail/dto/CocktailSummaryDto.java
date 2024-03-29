package com.cocktails.cocktail.dto;

import com.cocktails.cocktail.model.emuns.PreparationMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CocktailSummaryDto {

    private Long id;
    private String name;
    private PreparationMethod preparationMethod;
    private String glassIcon;

}
