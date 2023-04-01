package com.cocktails.dto;

import com.cocktails.domain.enums.IngredientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto {

    private Long id;
    private String name;
    private IngredientType type;

}
