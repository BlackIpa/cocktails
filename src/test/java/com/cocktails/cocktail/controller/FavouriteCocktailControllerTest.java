package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.model.emuns.PreparationMethod;
import com.cocktails.cocktail.service.FavouriteCocktailService;
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

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FavouriteCocktailControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FavouriteCocktailService favouriteCocktailService;

    @Mock
    private Principal principal;

    @InjectMocks
    private FavouriteCocktailController favouriteCocktailController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(favouriteCocktailController).build();
    }

    @Test
    @SneakyThrows
    public void addFavouriteCocktail_ShouldReturnResult() {
        // given
        val cocktailId = 1L;
        val email = "user@example.com";
        val successMessage = "Cocktail added to favourites";

        // when
        when(principal.getName()).thenReturn(email);
        when(favouriteCocktailService.addFavouriteCocktail(email, cocktailId)).thenReturn(successMessage);

        // then
        mockMvc.perform(post("/favourites/{cocktailId}", cocktailId)
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
        verify(favouriteCocktailService).addFavouriteCocktail(email, cocktailId);
    }

    @Test
    @SneakyThrows
    public void findAllFavouriteCocktails_ShouldReturnResult() {
        // given
        val email = "user@example.com";
        val cocktails = createCocktailSummaryDtoList();

        // when
        when(principal.getName()).thenReturn(email);
        when(favouriteCocktailService.getUserFavouriteCocktails(email)).thenReturn(cocktails);

        // then
        mockMvc.perform(get("/favourites")
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Whiskey Sour")))
                .andExpect(jsonPath("$[1].name", is("Whiskey Smash")));
        verify(favouriteCocktailService).getUserFavouriteCocktails(email);
    }

    @Test
    @SneakyThrows
    public void removeFavouriteCocktail_ShouldReturnResult() {
        // given
        Long cocktailId = 1L;
        String email = "user@example.com";
        String successMessage = "Cocktail removed from favourites";

        // when
        when(principal.getName()).thenReturn(email);
        doNothing().when(favouriteCocktailService).removeFavouriteCocktail(email, cocktailId);

        // then
        mockMvc.perform(delete("/favourites/{cocktailId}", cocktailId)
                        .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
        verify(favouriteCocktailService).removeFavouriteCocktail(email, cocktailId);
    }

    private List<CocktailSummaryDto> createCocktailSummaryDtoList() {
        return Arrays.asList(
                CocktailSummaryDto.builder().id(1L).name("Whiskey Sour")
                        .preparationMethod(PreparationMethod.STIRRED).glassIcon("highball-icon").build(),
                CocktailSummaryDto.builder().id(2L).name("Whiskey Smash")
                        .preparationMethod(PreparationMethod.STIRRED).glassIcon("martini-icon").build()
        );
    }

}