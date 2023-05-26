package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.model.Step;
import com.cocktails.cocktail.dto.StepDto;
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
