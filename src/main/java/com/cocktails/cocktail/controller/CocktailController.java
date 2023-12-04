package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.CocktailDetailsDto;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.service.impl.CocktailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/cocktails")
@Slf4j
@RequiredArgsConstructor
public class CocktailController {

    private final CocktailServiceImpl cocktailService;

    @GetMapping
    public ResponseEntity<List<CocktailSummaryDto>> findCocktails(@RequestParam(required = false) String name) {
        log.info("Show cocktails list");
        final List<CocktailSummaryDto> cocktailsList = name == null
                ? cocktailService.readAll()
                : cocktailService.findCocktailsByName(name);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(cocktailsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CocktailDetailsDto> findCocktailById(@PathVariable Long id) {
        log.info("Find cocktail by id");
        try {
            CocktailDetailsDto cocktailDetailsDto = cocktailService.findById(id);
            return ResponseEntity.ok(cocktailDetailsDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> findCocktailsByNamePart(@RequestParam String name) {
        log.info("Display cocktails which contain " + name + " in name");
        return ResponseEntity.ok(cocktailService.findCocktailsByNamePart(name));
    }

}
