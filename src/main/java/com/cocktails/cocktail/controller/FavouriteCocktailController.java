package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.service.FavouriteCocktailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/favourites")
@Slf4j
@RequiredArgsConstructor
public class FavouriteCocktailController {

    private final FavouriteCocktailService favouriteCocktailService;

    @PostMapping("/{cocktailId}")
    public ResponseEntity<String> addFavouriteCocktail(@PathVariable Long cocktailId, Principal principal) {
        val email = principal.getName();
        val message = favouriteCocktailService.addFavouriteCocktail(email, cocktailId);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<CocktailSummaryDto>> findAllFavouriteCocktails(Principal principal) {
        val favouriteCocktails = favouriteCocktailService.getUserFavouriteCocktails(principal.getName());
        return ResponseEntity.ok()
                .body(favouriteCocktails);
    }

    @DeleteMapping("/{cocktailId}")
    public ResponseEntity<String> removeFavouriteCocktail(@PathVariable Long cocktailId, Principal principal) {
        val email = principal.getName();
        favouriteCocktailService.removeFavouriteCocktail(email, cocktailId);
        return ResponseEntity.ok("Cocktail removed from favourites");
    }

}
