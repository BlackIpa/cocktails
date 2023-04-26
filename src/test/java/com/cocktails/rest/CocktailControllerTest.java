package com.cocktails.rest;

import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;
import com.cocktails.service.CocktailService;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CocktailControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CocktailController cocktailController;

    @Mock
    private CocktailService cocktailService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cocktailController).build();
    }

    @Test
    @SneakyThrows
    public void findAll_ShouldReturnResult() {

        // given
        val cocktails = Arrays.asList(
                new CocktailSummaryDto(1L, "Casino"),
                new CocktailSummaryDto(2L, "Martini"));
        when(cocktailService.readAll()).thenReturn(cocktails);

        // when & then
        mockMvc.perform(get("/cocktails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Casino")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Martini")));

        verify(cocktailService, times(1)).readAll();
    }

    @Test
    @SneakyThrows
    public void findById_ShouldReturnResult() {

        // given
        val id = 1L;
        val cocktailDetailsDto = new CocktailDetailsDto(id, "Casino", Collections.emptyList(),
                Collections.emptyList());
        when(cocktailService.findById(id)).thenReturn(cocktailDetailsDto);

        // when & then
        mockMvc.perform(get("/cocktails/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Casino")))
                .andExpect(jsonPath("$.cocktailIngredientsDto", is(Collections.emptyList())))
                .andExpect(jsonPath("$.stepsDto", is(Collections.emptyList())));

        verify(cocktailService, times(1)).findById(id);
    }

    @Test
    @SneakyThrows
    public void findById_ShouldThrow() {

        // given
        val id = 1L;
        when(cocktailService.findById(id)).thenThrow(new EntityNotFoundException("Not found"));

        // when & then
        mockMvc.perform(get("/cocktails/{id}", id))
                .andExpect(status().isNotFound());

        verify(cocktailService, times(1)).findById(id);
    }

}