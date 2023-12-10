package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.GroupedIngredientsDto;
import com.cocktails.cocktail.dto.IngredientDto;
import com.cocktails.cocktail.model.emuns.IngredientType;
import com.cocktails.cocktail.service.impl.IngredientServiceImpl;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IngredientServiceImpl ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    @SneakyThrows
    public void findAllGroupedByType_ShouldReturnGroupedIngredients() throws Exception {
        // given
        val mockResponse = createMockGroupedIngredientsDto();
        when(ingredientService.findAllIngredientsGroupedByType()).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientsGroupedByType").exists())
                .andExpect(jsonPath("$.ingredientsGroupedByType.SPIRIT[0].name").value("Vodka"));
    }

    @Test
    @SneakyThrows
    public void findAllGroupedByType_ShouldReturnEmptyDto_WhenNoIngredientsFound() {
        // given
        val emptyDto = GroupedIngredientsDto.builder().build();
        when(ingredientService.findAllIngredientsGroupedByType()).thenReturn(emptyDto);

        // when & then
        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientsGroupedByType").isEmpty());
    }

    @Test
    @SneakyThrows
    public void findIngredientsByNamePart_ShouldReturnResult() {
        // given
        val keyword = "wod";
        val mockResponse = createMockGroupedIngredientsDto();
        when(ingredientService.findIngredientsByNamePart(keyword)).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(get("/ingredients/search")
                        .param("name", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientsGroupedByType").exists())
                .andExpect(jsonPath("$.ingredientsGroupedByType.SPIRIT[0].name").value("Vodka"));
    }

    private GroupedIngredientsDto createMockGroupedIngredientsDto() {
        Map<IngredientType, List<IngredientDto>> groupedIngredients = new HashMap<>();
        groupedIngredients.put(IngredientType.SPIRIT, Collections.singletonList(
                IngredientDto.builder().id(1L).name("Vodka").type(IngredientType.SPIRIT).build()));

        return GroupedIngredientsDto.builder().ingredientsGroupedByType(groupedIngredients).build();
    }
}