package com.cocktails.rest;

import com.cocktails.domain.Cocktail;
import com.cocktails.service.CocktailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cocktails")
public class CocktailController {

    private final Logger log = LoggerFactory.getLogger(CocktailController.class);

    private final CocktailService cocktailService;

    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }

    @GetMapping()
    public ResponseEntity<List<Cocktail>> readAll() {
        log.debug("Read all cocktails");
        return ResponseEntity.ok().body(cocktailService.readAll());
    }
}
