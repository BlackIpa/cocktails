package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.GroupedIngredientsDto;
import com.cocktails.cocktail.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredients")
@Slf4j
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<GroupedIngredientsDto> findAllIngredients() {
        return ResponseEntity.ok(ingredientService.findAllIngredientsGroupedByType());
    }

    @GetMapping("/search")
    public ResponseEntity<GroupedIngredientsDto> findIngredientsByNamePart(@RequestParam String name) {
        val groupedIngredients = ingredientService.findIngredientsByNamePart(name);
        return ResponseEntity.ok(groupedIngredients);
    }
}
