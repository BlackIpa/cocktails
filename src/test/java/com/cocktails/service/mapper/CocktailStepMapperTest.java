package com.cocktails.service.mapper;

import com.cocktails.domain.Step;
import com.cocktails.dto.StepDto;
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
        val step = new Step();
        step.setId(1L);
        step.setStepNumber(1);
        step.setDescription("Dummy Move");

        // when
        val stepDto = cocktailStepMapper.stepToDto(step);

        // then
        assertEquals(step.getId(), stepDto.getId());
        assertEquals(step.getStepNumber(), stepDto.getStepNumber());
        assertEquals(step.getDescription(), stepDto.getDescription());
    }

}