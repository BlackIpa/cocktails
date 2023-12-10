package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.dto.StepDto;
import com.cocktails.cocktail.model.Step;
import org.springframework.stereotype.Component;

@Component
public class CocktailStepMapper {

    public StepDto stepToDto(Step step) {
        return StepDto.builder()
                .id(step.getId()).stepNumber(step.getStepNumber()).description(step.getDescription()).build();
    }

}
