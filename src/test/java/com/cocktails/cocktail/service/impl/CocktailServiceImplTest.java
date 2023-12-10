package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.CocktailDetailsDto;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.model.Glass;
import com.cocktails.cocktail.model.emuns.PreparationMethod;
import com.cocktails.cocktail.repository.CocktailRepository;
import com.cocktails.cocktail.service.mapper.CocktailMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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
        val glass = Glass.builder().name("name").iconName("icon").build();
        val cocktail = Cocktail.builder().id(id).name("Whiskey Sour")
                .preparationMethod(PreparationMethod.STIRRED).glass(glass).build();
        val cocktailDetailsDto = CocktailDetailsDto.builder().build();
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
        String keyword = "whi";

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
        val summaryDto1 = createCocktailSummaryDto("Whiskey Sour", "highball-icon");
        val summaryDto2 = createCocktailSummaryDto("Whiskey Smash", "martini-icon");
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
        return Arrays.asList(createCocktailSummaryDto("Whiskey Sour", "highball-icon"),
                createCocktailSummaryDto("Whiskey Smash", "martini-icon"));
    }

    private List<Cocktail> createCocktailList() {
        val glass = Glass.builder().id(1L).name("name").iconName("icon").build();
        return Arrays.asList(
                Cocktail.builder().id(1L).name("Whiskey Sour")
                        .preparationMethod(PreparationMethod.STIRRED).glass(glass).build(),
                Cocktail.builder().id(2L).name("Whiskey Smash")
                        .preparationMethod(PreparationMethod.STIRRED).glass(glass).build()
        );
    }

    private CocktailSummaryDto createCocktailSummaryDto(final String name, final String icon) {
        return CocktailSummaryDto.builder().id(1L).name(name)
                .preparationMethod(PreparationMethod.STIRRED).glassIcon(icon).build();
    }

}