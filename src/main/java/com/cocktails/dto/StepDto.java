package com.cocktails.dto;

import com.cocktails.domain.enums.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepDto {

    private Long id;
    private Integer stepNumber;
    private String description;

}
