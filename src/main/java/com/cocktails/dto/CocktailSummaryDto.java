package com.cocktails.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CocktailSummaryDto {

    private Long id;
    private String name;
    private String recipe;
}
