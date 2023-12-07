package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.CocktailDetailsDto;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.repository.CocktailRepository;
import com.cocktails.cocktail.service.CocktailService;
import com.cocktails.cocktail.service.mapper.CocktailMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository cocktailRepository;
    private final CocktailMapper cocktailMapper;

    @Override
    public List<CocktailSummaryDto> readAll() {
        val cocktailsSummaries =  cocktailRepository.findAllSummaries();
        if (cocktailsSummaries.isEmpty()) {
            return Collections.emptyList();
        }
        return cocktailsSummaries;
    }

    @Override
    public CocktailDetailsDto findById(Long id) {
        val cocktail = cocktailRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cocktail not found with id: " + id));
        return cocktailMapper.cocktailToDetailsDto(cocktail);
    }

    @Override
    public List<CocktailSummaryDto> findCocktailsByName(String cocktailName) {
        if (cocktailName == null || cocktailName.length() < 3) {
            return Collections.emptyList();
        }
        return cocktailRepository.findByName(cocktailName);
    }

    @Override
    public List<String> findCocktailsByNamePart(String cocktailName) {
        if (cocktailName == null || cocktailName.length() < 3) {
            return Collections.emptyList();
        }
        return cocktailRepository.findByNamePart(cocktailName);
    }

    @Override
    public List<CocktailSummaryDto> findCocktailsByIngredient(String ingredientName) {
        val cocktails = cocktailRepository.findByCocktailIngredients_Ingredient_NameIgnoreCase(ingredientName);
        return cocktails.stream()
                .map(cocktailMapper::cocktailToSummaryDto)
                .collect(Collectors.toList());
    }


}
