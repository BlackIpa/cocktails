package com.cocktails.rest;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;
import com.cocktails.service.CocktailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final Logger log = LoggerFactory.getLogger(CocktailController.class);

    private final CocktailService cocktailService;

    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping
    public ResponseEntity<List<CocktailSummaryDto>> readAll() {
        log.debug("Read all cocktails");
        return ResponseEntity.ok().body(cocktailService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CocktailDetailsDto> readById(@PathVariable Long id) {
        try {
            CocktailDetailsDto cocktailDetailsDto = cocktailService.findById(id);
            return ResponseEntity.ok(cocktailDetailsDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
