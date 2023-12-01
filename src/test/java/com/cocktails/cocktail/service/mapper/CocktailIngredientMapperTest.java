package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.model.CocktailIngredient;
import com.cocktails.cocktail.model.Ingredient;
import com.cocktails.cocktail.model.emuns.IngredientType;
import com.cocktails.cocktail.model.emuns.Unit;
import com.cocktails.cocktail.dto.IngredientDto;
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
        val ingredient = Ingredient.builder().id(1L).name("Vodka").type(IngredientType.SPIRIT).build();

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