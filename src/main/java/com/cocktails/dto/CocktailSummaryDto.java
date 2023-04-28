package com.cocktails.dto;

import com.cocktails.domain.enums.PreparationMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CocktailSummaryDto {

    private Long id;
    private String name;
    private PreparationMethod preparationMethod;
    private String glassIcon;

}
