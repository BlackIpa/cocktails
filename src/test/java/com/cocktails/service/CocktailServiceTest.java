package com.cocktails.service;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;
import com.cocktails.repository.CocktailRepository;
import com.cocktails.service.mapper.CocktailMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CocktailServiceTest {

    @InjectMocks
    private CocktailService cocktailService;

    @Mock
    private CocktailRepository cocktailRepository;

    @Mock
    private CocktailMapper cocktailMapper;

    @Test
    public void readAll_ShouldReturnResult() {

        // given
        val cocktail = new CocktailSummaryDto(1L, "Casino");
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
        cocktail.setId(id);
        val cocktailDetailsDto = new CocktailDetailsDto();
        cocktailDetailsDto.setId(id);

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

}