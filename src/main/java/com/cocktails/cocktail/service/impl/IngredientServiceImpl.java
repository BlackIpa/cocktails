package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.GroupedIngredientsDto;
import com.cocktails.cocktail.dto.IngredientDto;
import com.cocktails.cocktail.repository.IngredientRepository;
import com.cocktails.cocktail.service.IngredientService;
import com.cocktails.cocktail.service.mapper.IngredientMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final IngredientMapper ingredientMapper;

    @Override
    public GroupedIngredientsDto findAllIngredientsGroupedByType() {
        val ingredients = ingredientRepository.findAll();
        if (ingredients.isEmpty()) {
            return GroupedIngredientsDto.builder().ingredientsGroupedByType(new HashMap<>()).build();
        }
        val groupedByType = ingredients.stream()
                .map(ingredientMapper::ingredientToDto)
                .collect(Collectors.groupingBy(IngredientDto::getType));
        return GroupedIngredientsDto.builder().ingredientsGroupedByType(groupedByType).build();
    }

    @Override
    public GroupedIngredientsDto findIngredientsByNamePart(String ingredientName) {
        if (ingredientName.length() < 3) {
            return GroupedIngredientsDto.builder().ingredientsGroupedByType(new HashMap<>()).build();
        }
        val ingredients = ingredientRepository.findByNameContainingIgnoreCase(ingredientName);
        val groupedByType = ingredients.stream()
                .map(ingredientMapper::ingredientToDto)
                .collect(Collectors.groupingBy(IngredientDto::getType));
        return GroupedIngredientsDto.builder().ingredientsGroupedByType(groupedByType).build();
    }
}
