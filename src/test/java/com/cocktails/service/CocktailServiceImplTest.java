package com.cocktails.service;

import com.cocktails.domain.Cocktail;
import com.cocktails.domain.enums.PreparationMethod;
import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;
import com.cocktails.repository.CocktailRepository;
import com.cocktails.service.impl.*;
import com.cocktails.service.mapper.CocktailMapper;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CocktailServiceImplTest {

    @InjectMocks
    private CocktailServiceImpl cocktailService;

    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private CocktailMapper cocktailMapper;

    @Test
    public void readAll_ShouldReturnResult() {

        // given
        val cocktail = new CocktailSummaryDto(1L, "Casino", PreparationMethod.STIRRED, "highball-icon");
        when(cocktailRepository.findAllSummaries()).thenReturn(List.of(cocktail));

        // when
        val result = cocktailService.readAll();

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(result.get(0).getName(), cocktail.getName());
        verify(cocktailRepository).findAllSummaries();
        verifyNoMoreInteractions(cocktailRepository);
    }

    @Test
    public void findById_ShouldReturnResult() {

        // given
        val id = 1L;
        val cocktail = new Cocktail();
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
    public void findByName_ShouldReturnResult() {

        // given
        String keyword = "whi";
        val summary1 = new CocktailSummaryDto(1L, "Whiskey Smash", PreparationMethod.STIRRED, "highball-icon");
        val summary2 = new CocktailSummaryDto(2L, "Whiskey Sour", PreparationMethod.STIRRED, "highball-icon");
        val summaries = Arrays.asList(summary1, summary2);

        when(cocktailRepository.findByName(keyword)).thenReturn(summaries);

        // when
        val result = cocktailService.findByName(keyword);

        // then
        assertEquals(summaries, result);
    }

    @Test
    public void findByName_ShouldReturnEmptyList() {

        // given
        String keyword = "wh";

        // when
        val result = cocktailService.findByName(keyword);

        // then
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    public void findByNamePart_ShouldReturnResult() {

        // given
        String keyword = "whi";
        val names = Arrays.asList("Whiskey Sour", "Whiskey Smash");

        when(cocktailRepository.findByNamePart(keyword)).thenReturn(names);

        // when
        val result = cocktailService.findByNamePart(keyword);

        // then
        assertEquals(names, result);
    }

    @Test
    public void findByNamePart_ShouldReturnEmptyList() {

        // given
        String keyword = "wh";

        // when
        val result = cocktailService.findByNamePart(keyword);

        // then
        assertEquals(Collections.emptyList(), result);
    }
}