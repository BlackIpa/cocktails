package com.cocktails.service;

import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;

import java.util.List;

public interface CocktailService {

    List<CocktailSummaryDto> readAll();

    CocktailDetailsDto findById(Long id);

    List<CocktailSummaryDto> findByName(String keyword);

    List<String> findByNamePart(String keyword);
}
