package com.cocktails.service.mapper;

import com.cocktails.domain.CocktailIngredient;
import com.cocktails.domain.Ingredient;
import com.cocktails.domain.enums.IngredientType;
import com.cocktails.dto.IngredientDto;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CocktailIngredientMapperTest {

    private CocktailIngredientMapper cocktailIngredientMapper;

    @BeforeEach
    public void setUp() {
        cocktailIngredientMapper = new CocktailIngredientMapper();
    }

    @Test
    public void cocktailIngredientToDto_ShouldReturnResult() {

        // given
        val ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Vodka");
        ingredient.setType(IngredientType.SPIRIT);

        val cocktailIngredient = new CocktailIngredient();
        cocktailIngredient.setId(1L);
        cocktailIngredient.setQuantity("2.0");
        cocktailIngredient.setIngredient(ingredient);

        // when
        val cocktailIngredientDto = cocktailIngredientMapper.cocktailIngredientToDto(cocktailIngredient);

        // then
        assertEquals(cocktailIngredient.getId(), cocktailIngredientDto.getId());
        assertEquals(cocktailIngredient.getQuantity(), cocktailIngredientDto.getQuantity());

        IngredientDto ingredientDto = cocktailIngredientDto.getIngredient();
        assertEquals(ingredient.getId(), ingredientDto.getId());
        assertEquals(ingredient.getName(), ingredientDto.getName());
        assertEquals(ingredient.getType(), ingredientDto.getType());
    }
}