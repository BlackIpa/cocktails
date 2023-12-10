package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.model.Step;
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
        // given
        val step = Step.builder().id(1L).stepNumber(1).description("Dummy Move").build();

        // when
        val stepDto = cocktailStepMapper.stepToDto(step);

        // then
        assertEquals(step.getId(), stepDto.getId());
        assertEquals(step.getStepNumber(), stepDto.getStepNumber());
        assertEquals(step.getDescription(), stepDto.getDescription());
    }

}