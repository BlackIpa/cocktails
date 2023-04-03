package com.cocktails.dto;

import com.cocktails.domain.enums.IngredientType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepDto {

    private Long id;
    private Integer stepNumber;
    private String description;

}
