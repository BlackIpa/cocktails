package com.cocktails.service.mapper;

import com.cocktails.domain.CocktailIngredient;
import com.cocktails.domain.Ingredient;
import com.cocktails.domain.enums.IngredientType;
import com.cocktails.domain.enums.Unit;
import com.cocktails.dto.IngredientDto;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CocktailIngredientMapperTest {

    private CocktailIngredientMapper cocktailIngredientMapper;

    @BeforeEach
    public void setUp() {
        cocktailIngredientMapper = new CocktailIngredientMapper();
    }

    @Test
    public void cocktailIngredientToDto_ShouldReturnResult() {

        val ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Vodka");
        ingredient.setType(IngredientType.SPIRIT);

        val cocktailIngredient = new CocktailIngredient();
        cocktailIngredient.setId(1L);
        cocktailIngredient.setQuantity(BigDecimal.valueOf(2.0));
        cocktailIngredient.setUnit(Unit.OZ);
        cocktailIngredient.setIngredient(ingredient);

        val cocktailIngredientDto = cocktailIngredientMapper.cocktailIngredientToDto(cocktailIngredient);

        assertEquals(cocktailIngredient.getId(), cocktailIngredientDto.getId());
        assertEquals(cocktailIngredient.getQuantity(), cocktailIngredientDto.getQuantity());
        assertEquals(cocktailIngredient.getUnit(), cocktailIngredientDto.getUnit());

        IngredientDto ingredientDto = cocktailIngredientDto.getIngredient();
        assertEquals(ingredient.getId(), ingredientDto.getId());
        assertEquals(ingredient.getName(), ingredientDto.getName());
        assertEquals(ingredient.getType(), ingredientDto.getType());
    }
}