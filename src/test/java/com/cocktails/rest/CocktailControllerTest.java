package com.cocktails.rest;

import com.cocktails.service.CocktailService;
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
class CocktailControllerTest {

    @Mock
    private CocktailService cocktailService;

    @InjectMocks
    private CocktailController cocktailController;

    @Test
    public void readAll_ShouldReturnResult() {
        // given
        when(cocktailService.readAll()).thenReturn(List.of());

        // when
        val response = cocktailController.readAll();

        //then
        assertNotNull(response);
        verify(cocktailService).readAll();
        verifyNoMoreInteractions(cocktailService);
    }
}