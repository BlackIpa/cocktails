package com.cocktails.cocktail.service.impl;

import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.model.FavouriteCocktail;
import com.cocktails.cocktail.model.Glass;
import com.cocktails.cocktail.model.User;
import com.cocktails.cocktail.model.emuns.PreparationMethod;
import com.cocktails.cocktail.repository.CocktailRepository;
import com.cocktails.cocktail.repository.FavouriteCocktailRepository;
import com.cocktails.cocktail.repository.UserRepository;
import com.cocktails.cocktail.service.mapper.CocktailMapper;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavouriteCocktailServiceImplTest {

    @Mock
    private CocktailMapper cocktailMapper;

    @Mock
    private FavouriteCocktailRepository favouriteCocktailRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CocktailRepository cocktailRepository;

    @InjectMocks
    private FavouriteCocktailServiceImpl favouriteCocktailService;

    @Test
    public void addFavouriteCocktail_ShouldReturnResult() {
        // given
        val cocktailId = 1L;
        val email = "user@example.com";
        val successMessage = "Cocktail added to favourites";
        val cocktail = createCocktail();
        val user = User.builder().email(email).build();

        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(cocktailRepository.findById(cocktailId)).thenReturn(Optional.of(cocktail));
        when(favouriteCocktailRepository.findByUserIdAndCocktailId(user.getId(), cocktailId)).thenReturn(Optional.empty());

        // then
        val result = favouriteCocktailService.addFavouriteCocktail(email, cocktailId);
        assertEquals("Cocktail added to favourites", result);
        verify(favouriteCocktailRepository).save(any(FavouriteCocktail.class));
    }

    @Test
    public void getUserFavouriteCocktails_ShouldReturnResult() {
        // given
        val email = "user@example.com";
        val cocktail = createCocktail();
        val cocktailSummaryDto = createCocktailSummaryDto();
        val user = User.builder().id(1L).email(email).build();

        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(favouriteCocktailRepository.findCocktailsByUserId(user.getId())).thenReturn(List.of(cocktail));
        when(cocktailMapper.cocktailToSummaryDto(cocktail)).thenReturn(cocktailSummaryDto);

        // then
        val result = favouriteCocktailService.getUserFavouriteCocktails(email);
        assertEquals(1, result.size());
        assertEquals(cocktailSummaryDto, result.get(0));
        verify(cocktailMapper).cocktailToSummaryDto(cocktail);
    }

    @Test
    public void removeFavouriteCocktail_ShouldRemoveCocktailWhenPresent() {
        // given
        val cocktailId = 1L;
        val email = "user@example.com";
        val user = User.builder().id(1L).email(email).build();
        val favouriteCocktail = FavouriteCocktail.builder().build();

        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(favouriteCocktailRepository.findByUserIdAndCocktailId(user.getId(), cocktailId))
                .thenReturn(Optional.of(favouriteCocktail));

        // then
        favouriteCocktailService.removeFavouriteCocktail(email, cocktailId);
        verify(favouriteCocktailRepository).delete(favouriteCocktail);
    }

    @Test
    public void removeFavouriteCocktail_ShouldDoNothingWhenCocktailNotPresent() {
        // given
        val cocktailId = 1L;
        val email = "user@example.com";
        val user = User.builder().id(1L).email(email).build();

        // when
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(favouriteCocktailRepository.findByUserIdAndCocktailId(user.getId(), cocktailId)).thenReturn(Optional.empty());

        // then
        favouriteCocktailService.removeFavouriteCocktail(email, cocktailId);
        verify(favouriteCocktailRepository, never()).delete(any(FavouriteCocktail.class));
    }

    @Test
    public void removeFavouriteCocktail_ShouldThrowExceptionForInvalidEmail() {
        // given
        val cocktailId = 1L;
        String invalidEmail = "user@example.com";

        // when
        when(userRepository.findByEmail(invalidEmail)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            favouriteCocktailService.removeFavouriteCocktail(invalidEmail, cocktailId);
        });
    }

    private Cocktail createCocktail() {
        val glass = Glass.builder().id(1L).name("name").iconName("glass").build();
        return Cocktail.builder().id(1L).name("Whiskey Sour")
                .preparationMethod(PreparationMethod.STIRRED).glass(glass).build();
    }

    private CocktailSummaryDto createCocktailSummaryDto() {
        return CocktailSummaryDto.builder().id(1L).name("Whiskey Sour")
                .preparationMethod(PreparationMethod.STIRRED).glassIcon("glass").build();
    }

}