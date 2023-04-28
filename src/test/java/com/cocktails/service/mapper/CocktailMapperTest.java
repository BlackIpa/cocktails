package com.cocktails.service.mapper;

import com.cocktails.domain.*;
import com.cocktails.domain.enums.IngredientType;
import com.cocktails.domain.enums.PreparationMethod;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CocktailMapperTest {

    private CocktailMapper cocktailMapper;

    @BeforeEach
    public void setUp() {
        cocktailMapper = new CocktailMapper(new CocktailIngredientMapper(), new CocktailStepMapper());
    }

    @Test
    public void cocktailToDetailsDto_ShouldReturnResult() {

        // given
        val id = 1L;
        val cocktail = new Cocktail();
        cocktail.setId(id);
        cocktail.setName("Casino");
        cocktail.setPreparationMethod(PreparationMethod.STIRRED);

        val glass = new Glass();
        glass.setId(id);
        glass.setName("Highball");
        glass.setIconName("highball-icon");

        cocktail.setGlass(glass);

        List<CocktailIngredient> cocktailIngredients = new ArrayList<>();
        val cocktailIngredient = new CocktailIngredient();
        cocktailIngredient.setQuantity(BigDecimal.valueOf(2));
        val ingredient = new Ingredient();
        ingredient.setName("Vodka");
        ingredient.setType(IngredientType.SPIRIT);
        cocktailIngredient.setIngredient(ingredient);
        cocktailIngredients.add(cocktailIngredient);

        List<Step> steps = new ArrayList<>();
        val step = new Step();
        step.setDescription("Add ingredients");
        step.setStepNumber(1);
        steps.add(step);

        cocktail.setCocktailIngredients(cocktailIngredients);
        cocktail.setSteps(steps);

        val cocktailDetailsDto = cocktailMapper.cocktailToDetailsDto(cocktail);

        assertEquals(cocktailDetailsDto.getId(), cocktail.getId());
        assertEquals(cocktailDetailsDto.getName(), cocktail.getName());
        assertEquals(cocktailDetailsDto.getPreparationMethod(), cocktail.getPreparationMethod());
        assertEquals(cocktailDetailsDto.getPreparationMethod(), cocktail.getPreparationMethod());
        assertEquals(cocktailDetailsDto.getGlassName(), cocktail.getGlass().getName());
        assertEquals(cocktailDetailsDto.getCocktailIngredientsDto().size(), cocktailIngredients.size());
        assertEquals(cocktailDetailsDto.getStepsDto().size(), cocktail.getSteps().size());
    }

}