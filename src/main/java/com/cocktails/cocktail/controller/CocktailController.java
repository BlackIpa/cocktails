package com.cocktails.cocktail.controller;

import com.cocktails.cocktail.dto.CocktailDetailsDto;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.service.impl.CocktailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/cocktails")
@Slf4j
public class CocktailController {

    private final CocktailServiceImpl cocktailService;

    public CocktailController(CocktailServiceImpl cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping
    public ResponseEntity<List<CocktailSummaryDto>> findCocktails(@RequestParam(required = false) String name) {
        log.info("Show cocktails list");
        final List<CocktailSummaryDto> cocktailsList = name == null
                ? cocktailService.readAll()
                : cocktailService.findByName(name);
        return ResponseEntity.ok().body(cocktailsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CocktailDetailsDto> findById(@PathVariable Long id) {
        log.info("Find cocktail by id");
        try {
            CocktailDetailsDto cocktailDetailsDto = cocktailService.findById(id);
            return ResponseEntity.ok(cocktailDetailsDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name")
    public ResponseEntity<List<String>> findByNamePart(@RequestParam String name) {
        log.info("Display cocktails which contain " + name + " in name");
        return ResponseEntity.ok(cocktailService.findByNamePart(name));
    }

}
