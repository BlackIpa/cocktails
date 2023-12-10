package com.cocktails.cocktail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StepDto {

    private Long id;
    private Integer stepNumber;
    private String description;

}
