package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.model.Step;
import com.cocktails.cocktail.service.mapper.CocktailStepMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CocktailStepMapperTest {

    private CocktailStepMapper cocktailStepMapper;

    @BeforeEach
    public void setUp() {
        cocktailStepMapper = new CocktailStepMapper();
    }

    @Test
    public void stepToDto_ShouldReturnResult() {

        val step = new Step();
        step.setId(1L);
        step.setStepNumber(1);
        step.setDescription("Dummy Move");

        val stepDto = cocktailStepMapper.stepToDto(step);

        assertEquals(step.getId(), stepDto.getId());
        assertEquals(step.getStepNumber(), stepDto.getStepNumber());
        assertEquals(step.getDescription(), stepDto.getDescription());
    }

}