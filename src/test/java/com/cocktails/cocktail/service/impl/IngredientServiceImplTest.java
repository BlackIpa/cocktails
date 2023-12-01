package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.IngredientDto;
import com.cocktails.cocktail.model.Ingredient;
import com.cocktails.cocktail.model.emuns.IngredientType;
import com.cocktails.cocktail.repository.IngredientRepository;
import com.cocktails.cocktail.service.mapper.IngredientMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Test
    public void findAllIngredientsGroupedByType_ShouldReturnResult() {
        // given
        val ingredientsList = List.of(Ingredient.builder()
                .name("Whiskey").type(IngredientType.SPIRIT).build());
        val ingredientDto = IngredientDto.builder()
                .name("Whiskey").type(IngredientType.SPIRIT).build();
        when(ingredientRepository.findAll()).thenReturn(ingredientsList);
        when(ingredientMapper.ingredientToDto(ingredientsList.get(0))).thenReturn(ingredientDto);

        // when
        val result = ingredientService.findAllIngredientsGroupedByType();

        // then
        assertNotNull(result);
        assertEquals(1, result.getIngredientsGroupedByType().size());
        val ingredientName = result.getIngredientsGroupedByType().get(IngredientType.SPIRIT)
                        .get(0).getName();
        assertEquals(ingredientsList.get(0).getName(), ingredientName);
    }

}