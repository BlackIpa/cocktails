package com.cocktails.service.mapper;

import com.cocktails.domain.CocktailIngredient;
import com.cocktails.domain.Ingredient;
import com.cocktails.domain.Step;
import com.cocktails.dto.CocktailIngredientDto;
import com.cocktails.dto.IngredientDto;
import com.cocktails.dto.StepDto;
import org.springframework.stereotype.Component;

@Component
public class CocktailStepMapper {

    public StepDto stepToDto(Step step) {
        StepDto stepDto = new StepDto();
        stepDto.setId(step.getId());
        stepDto.setStepNumber(step.getStepNumber());
        stepDto.setDescription(step.getDescription());
        return stepDto;
    }

}
