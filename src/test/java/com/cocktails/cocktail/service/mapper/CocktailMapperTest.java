package com.cocktails.cocktail.service.mapper;

import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.model.CocktailIngredient;
import com.cocktails.cocktail.model.Glass;
import com.cocktails.cocktail.model.Ingredient;
import com.cocktails.cocktail.model.Step;
import com.cocktails.cocktail.model.emuns.IngredientType;
import com.cocktails.cocktail.model.emuns.PreparationMethod;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CocktailMapperTest {

    @Mock
    private CocktailIngredientMapper cocktailIngredientMapper;

    @Mock
    private CocktailStepMapper stepMapper;

    @InjectMocks
    private CocktailMapper cocktailMapper;

    @Test
    public void cocktailToDetailsDto_ShouldReturnResult() {
        // given
        val cocktail = createCocktail("Whiskey Sour");
        List<CocktailIngredient> cocktailIngredients = new ArrayList<>();
        val cocktailIngredient = new CocktailIngredient();
        cocktailIngredient.setQuantity(BigDecimal.valueOf(2));
        val ingredient = Ingredient.builder().name("Vodka").type(IngredientType.SPIRIT).build();
        cocktailIngredient.setIngredient(ingredient);
        cocktailIngredients.add(cocktailIngredient);

        List<Step> steps = new ArrayList<>();
        val step = new Step();
        step.setDescription("Add ingredients");
        step.setStepNumber(1);
        steps.add(step);

        cocktail.setCocktailIngredients(cocktailIngredients);
        cocktail.setSteps(steps);

        val cocktailDetailsDto = cocktailMapper.cocktailToDetailsDto(cocktail);

        assertEquals(cocktailDetailsDto.getId(), cocktail.getId());
        assertEquals(cocktailDetailsDto.getName(), cocktail.getName());
        assertEquals(cocktailDetailsDto.getPreparationMethod(), cocktail.getPreparationMethod());
        assertEquals(cocktailDetailsDto.getPreparationMethod(), cocktail.getPreparationMethod());
        assertEquals(cocktailDetailsDto.getGlassName(), cocktail.getGlass().getName());
        assertEquals(cocktailDetailsDto.getCocktailIngredientsDto().size(), cocktailIngredients.size());
        assertEquals(cocktailDetailsDto.getStepsDto().size(), cocktail.getSteps().size());
    }

    @Test
    public void cocktailToSummaryDto_ShouldReturnResult() {
        val cocktail = createCocktail("Martini");

        val cocktailSummaryDto = cocktailMapper.cocktailToSummaryDto(cocktail);

        assertEquals(cocktail.getId(), cocktailSummaryDto.getId());
        assertEquals(cocktail.getName(), cocktailSummaryDto.getName());
        assertEquals(cocktail.getPreparationMethod(), cocktailSummaryDto.getPreparationMethod());
        assertEquals(cocktail.getGlass().getIconName(), cocktailSummaryDto.getGlassIcon());
    }

    private Cocktail createCocktail(final String name) {
        val glass = new Glass(1L, "name", "icon");
        return new Cocktail(1L, List.of(), name, List.of(), PreparationMethod.STIRRED, glass);
    }

}