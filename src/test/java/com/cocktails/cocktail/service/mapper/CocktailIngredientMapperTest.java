package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.dto.IngredientDto;
import com.cocktails.cocktail.model.CocktailIngredient;
import com.cocktails.cocktail.model.Ingredient;
import com.cocktails.cocktail.model.emuns.IngredientType;
import com.cocktails.cocktail.model.emuns.Unit;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CocktailIngredientMapperTest {

    @Mock
    private IngredientMapper ingredientMapper;

    @InjectMocks
    private CocktailIngredientMapper cocktailIngredientMapper;

    @Test
    public void cocktailIngredientToDto_ShouldReturnResult() {
        // given
        val ingredient = Ingredient.builder().id(1L).name("Vodka").type(IngredientType.SPIRIT).build();
        val cocktailIngredient = CocktailIngredient.builder().id(1L).quantity(BigDecimal.valueOf(2.0))
                .unit(Unit.OZ).ingredient(ingredient).build();
        val ingredientDto = IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .type(ingredient.getType())
                .build();
        when(ingredientMapper.ingredientToDto(ingredient)).thenReturn(ingredientDto);

        // when
        val cocktailIngredientDto = cocktailIngredientMapper.cocktailIngredientToDto(cocktailIngredient);

        // then
        assertEquals(cocktailIngredient.getId(), cocktailIngredientDto.getId());
        assertEquals(cocktailIngredient.getQuantity(), cocktailIngredientDto.getQuantity());
        assertEquals(cocktailIngredient.getUnit(), cocktailIngredientDto.getUnit());
        IngredientDto resultIngredientDto = cocktailIngredientDto.getIngredient();
        assertEquals(ingredient.getId(), resultIngredientDto.getId());
        assertEquals(ingredient.getName(), resultIngredientDto.getName());
        assertEquals(ingredient.getType(), resultIngredientDto.getType());
    }
}