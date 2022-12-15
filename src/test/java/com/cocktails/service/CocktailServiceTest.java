package com.cocktails.service;

import com.cocktails.domain.Cocktail;
import com.cocktails.repository.CocktailRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CocktailServiceTest {

    @Mock
    private CocktailRepository cocktailRepository;

    @InjectMocks
    private CocktailService cocktailService;

    @Test
    public void readAll_ShouldReturnResult() {
        // given
        val cocktail = new Cocktail();
        cocktail.setName("Old Fashioned");
        when(cocktailRepository.findAll()).thenReturn(List.of(cocktail));

        // when
        val result = cocktailService.readAll();

        // then
        assertEquals(1, result.size());
        assertEquals(result.get(0).getName(), cocktail.getName());
        verify(cocktailRepository).findAll();
        verifyNoMoreInteractions(cocktailRepository);
    }
}