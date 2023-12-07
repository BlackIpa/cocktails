package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.model.Glass;
import com.cocktails.cocktail.model.Ingredient;
import com.cocktails.cocktail.model.emuns.PreparationMethod;
import com.cocktails.cocktail.dto.CocktailDetailsDto;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.repository.CocktailRepository;
import com.cocktails.cocktail.service.impl.CocktailServiceImpl;
import com.cocktails.cocktail.service.mapper.CocktailMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CocktailServiceImplTest {

    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private CocktailMapper cocktailMapper;

    @InjectMocks
    private CocktailServiceImpl cocktailService;

    @Test
    public void readAll_ShouldReturnResult() {
        // given
        val cocktails = createCocktailSummaryDtoList();
        when(cocktailRepository.findAllSummaries()).thenReturn(cocktails);

        // when
        val result = cocktailService.readAll();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(result.get(0).getName(), cocktails.get(0).getName());
        verify(cocktailRepository).findAllSummaries();
        verifyNoMoreInteractions(cocktailRepository);
    }

    @Test
    public void findById_ShouldReturnResult() {
        // given
        val id = 1L;
        val glass = new Glass(id, "name", "icon");
        val cocktail = new Cocktail(id, List.of(), "Whiskey Sour", List.of(), PreparationMethod.STIRRED, glass);
        val cocktailDetailsDto = new CocktailDetailsDto();
        when(cocktailRepository.findById(id)).thenReturn(Optional.of(cocktail));
        when(cocktailMapper.cocktailToDetailsDto(cocktail)).thenReturn(cocktailDetailsDto);

        // when
        val result = cocktailService.findById(id);

        // then
        assertNotNull(result);
        assertEquals(cocktailDetailsDto, result);
    }

    @Test
    public void findById_ShouldThrow() {
        // given
        val id = 1L;
        when(cocktailRepository.findById(id)).thenReturn(Optional.empty());

        // then
        assertThrows(EntityNotFoundException.class, () -> cocktailService.findById(id));
    }

    @Test
    public void findCocktailsByName_ShouldReturnResult() {
        // given
        String keyword = "whi";
        val cocktails = createCocktailSummaryDtoList();
        when(cocktailRepository.findByName(keyword)).thenReturn(cocktails);

        // when
        val result = cocktailService.findCocktailsByName(keyword);

        // then
        assertEquals(cocktails, result);
    }

    @Test
    public void findCocktailsByName_ShouldReturnEmptyList() {
        // given
        String keyword = "wh";

        // when
        val result = cocktailService.findCocktailsByName(keyword);

        // then
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void findCocktailsByNamePart_ShouldReturnResult() {
        // given
        String keyword = "whi";
        val names = Arrays.asList("Whiskey Sour", "Whiskey Smash");
        when(cocktailRepository.findByNamePart(keyword)).thenReturn(names);

        // when
        val result = cocktailService.findCocktailsByNamePart(keyword);

        // then
        assertEquals(names, result);
    }

    @Test
    public void findCocktailsByNamePart_ShouldReturnEmptyList() {
        // given
        String keyword = "wh";

        // when
        val result = cocktailService.findCocktailsByNamePart(keyword);

        // then
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void findCocktailsByIngredient_ShouldReturnResult() {
        // given
        String keyword = "whiskey";
        val cocktails = createCocktailList();
        val summaryDto1 = new CocktailSummaryDto(1L, "Whiskey Sour", PreparationMethod.STIRRED, "highball-icon");
        val summaryDto2 = new CocktailSummaryDto(2L, "Whiskey Smash", PreparationMethod.STIRRED, "martini-icon");
        when(cocktailRepository.findByCocktailIngredients_Ingredient_NameIgnoreCase(keyword)).thenReturn(cocktails);
        when(cocktailMapper.cocktailToSummaryDto(any(Cocktail.class))).thenReturn(summaryDto1, summaryDto2);

        // when
        val result = cocktailService.findCocktailsByIngredient(keyword);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(result.get(0).getName(), cocktails.get(0).getName());
        verify(cocktailRepository).findByCocktailIngredients_Ingredient_NameIgnoreCase(keyword);
        verifyNoMoreInteractions(cocktailRepository);
    }

    private List<CocktailSummaryDto> createCocktailSummaryDtoList() {
        return Arrays.asList(
                new CocktailSummaryDto(1L, "Whiskey Sour", PreparationMethod.STIRRED, "highball-icon"),
                new CocktailSummaryDto(2L, "Whiskey Smash", PreparationMethod.STIRRED, "martini-icon")
        );
    }

    private List<Cocktail> createCocktailList() {
        val glass = new Glass(1L, "name", "icon");
        return Arrays.asList(
                new Cocktail(1L, List.of(), "Whiskey Sour", List.of(), PreparationMethod.STIRRED, glass),
                new Cocktail(2L, List.of(), "Whiskey Smash", List.of(), PreparationMethod.STIRRED, glass)
        );
    }
}