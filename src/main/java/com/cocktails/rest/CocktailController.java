package com.cocktails.rest;

import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;
import com.cocktails.service.CocktailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public ResponseEntity<List<CocktailSummaryDto>> findAll() {
        log.info("Read all cocktails");
        return ResponseEntity.ok().body(cocktailService.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CocktailDetailsDto> findById(@PathVariable Long id) {
        try {
            CocktailDetailsDto cocktailDetailsDto = cocktailService.findById(id);
            return ResponseEntity.ok(cocktailDetailsDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CocktailSummaryDto>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(cocktailService.findByName(name));
    }

    @GetMapping("/name")
    public ResponseEntity<List<String>> findByNamePart(@RequestParam String name) {
        return ResponseEntity.ok(cocktailService.findByNamePart(name));
    }
}
