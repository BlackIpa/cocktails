package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.model.emuns.PreparationMethod;
import com.cocktails.cocktail.dto.CocktailDetailsDto;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.service.impl.CocktailServiceImpl;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CocktailControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CocktailServiceImpl cocktailService;

    @InjectMocks
    private CocktailController cocktailController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cocktailController).build();
    }

    @Test
    @SneakyThrows
    public void findCocktails_ShouldReturnResult() {
        // given
        val cocktails = createCocktailSummaryDtoList();
        when(cocktailService.readAll()).thenReturn(cocktails);

        // when & then
        mockMvc.perform(get("/cocktails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Whiskey Sour")))
                .andExpect(jsonPath("$[0].preparationMethod", is(PreparationMethod.STIRRED.name())))
                .andExpect(jsonPath("$[0].glassIcon", is("highball-icon")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Whiskey Smash")))
                .andExpect(jsonPath("$[1].preparationMethod", is(PreparationMethod.STIRRED.name())))
                .andExpect(jsonPath("$[1].glassIcon", is("martini-icon")));

        verify(cocktailService, times(1)).readAll();
    }

    @Test
    @SneakyThrows
    public void findCocktails_ShouldReturnResult_whenRequestParamProvided() {
        // given
        val keyword = "whi";
        val cocktails = createCocktailSummaryDtoList();
        when(cocktailService.findCocktailsByName(keyword)).thenReturn(cocktails);

        // when & then
        mockMvc.perform(get("/cocktails")
                        .param("name", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Whiskey Sour")))
                .andExpect(jsonPath("$[1].name", is("Whiskey Smash")));

        verify(cocktailService, times(1)).findCocktailsByName(keyword);
    }

    @Test
    @SneakyThrows
    public void findCocktailById_ShouldReturnResult() {
        // given
        val id = 1L;
        val cocktailDetailsDto = new CocktailDetailsDto(id, "Casino", PreparationMethod.STIRRED,
                "Glass", "highball-icon", Collections.emptyList(), Collections.emptyList());
        when(cocktailService.findById(id)).thenReturn(cocktailDetailsDto);

        // when & then
        mockMvc.perform(get("/cocktails/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Casino")))
                .andExpect(jsonPath("$.preparationMethod", is(PreparationMethod.STIRRED.name())))
                .andExpect(jsonPath("$.glassName", is("Glass")))
                .andExpect(jsonPath("$.glassIcon", is("highball-icon")))
                .andExpect(jsonPath("$.cocktailIngredientsDto", is(Collections.emptyList())))
                .andExpect(jsonPath("$.stepsDto", is(Collections.emptyList())));

        verify(cocktailService, times(1)).findById(id);
    }

    @Test
    @SneakyThrows
    public void findCocktailById_ShouldThrow() {
        // given
        val id = 1L;
        when(cocktailService.findById(id)).thenThrow(new EntityNotFoundException("Not found"));

        // when & then
        mockMvc.perform(get("/cocktails/{id}", id))
                .andExpect(status().isNotFound());

        verify(cocktailService, times(1)).findById(id);
    }

    @Test
    @SneakyThrows
    public void findCocktailsByNamePart_ShouldReturnResult() {
        // given
        val keyword = "whi";
        val names = Arrays.asList("Whiskey Sour", "Whiskey Smash");
        when(cocktailService.findCocktailsByNamePart(keyword)).thenReturn(names);

        // when & then
        mockMvc.perform(get("/cocktails/search")
                        .param("name", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("Whiskey Sour")))
                .andExpect(jsonPath("$[1]", is("Whiskey Smash")));

        verify(cocktailService, times(1)).findCocktailsByNamePart(keyword);
    }

    @Test
    @SneakyThrows
    public void findCocktailsByIngredient_ShouldReturnResult() {
        // given
        val keyword = "whi";
        val cocktails = createCocktailSummaryDtoList();
        when(cocktailService.findCocktailsByIngredient(keyword)).thenReturn(cocktails);

        // when & then
        mockMvc.perform(get("/cocktails/ingredient")
                        .param("name", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Whiskey Sour")))
                .andExpect(jsonPath("$[1].name", is("Whiskey Smash")));
        verify(cocktailService, times(1)).findCocktailsByIngredient(keyword);
    }

    private List<CocktailSummaryDto> createCocktailSummaryDtoList() {
        return Arrays.asList(
                new CocktailSummaryDto(1L, "Whiskey Sour", PreparationMethod.STIRRED, "highball-icon"),
                new CocktailSummaryDto(2L, "Whiskey Smash", PreparationMethod.STIRRED, "martini-icon")
        );
    }

}