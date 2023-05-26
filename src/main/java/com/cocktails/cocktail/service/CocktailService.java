package com.cocktails.cocktail.service;

import com.cocktails.cocktail.dto.CocktailDetailsDto;
import com.cocktails.cocktail.dto.CocktailSummaryDto;

import java.util.List;

public interface CocktailService {

    List<CocktailSummaryDto> readAll();

    CocktailDetailsDto findById(Long id);

    List<CocktailSummaryDto> findByName(String keyword);

    List<String> findByNamePart(String keyword);
}
