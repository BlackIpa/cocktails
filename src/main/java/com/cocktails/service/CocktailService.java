package com.cocktails.service;

import com.cocktails.domain.Cocktail;
import com.cocktails.repository.CocktailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CocktailService {

    private final Logger log = LoggerFactory.getLogger(CocktailService.class);

    private final CocktailRepository cocktailRepository;

    public CocktailService(CocktailRepository cocktailRepository) {
        this.cocktailRepository = cocktailRepository;
    }

    public List<Cocktail> readAll() {
        log.debug("Get all cocktails");
        return cocktailRepository.findAll();
    }
}
