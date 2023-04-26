package com.cocktails.service;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;
import com.cocktails.repository.CocktailRepository;
import com.cocktails.service.mapper.CocktailMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CocktailService {

    private final CocktailRepository cocktailRepository;
    private final CocktailMapper cocktailMapper;

    public CocktailService(CocktailRepository cocktailRepository, CocktailMapper cocktailMapper) {
        this.cocktailRepository = cocktailRepository;
        this.cocktailMapper = cocktailMapper;
    }

    public List<CocktailSummaryDto> readAll() {
        return cocktailRepository.findAllSummaries();
    }

    public CocktailDetailsDto findById(Long id) {
        Cocktail cocktail = cocktailRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Cocktail not found with id: " + id));
        return cocktailMapper.cocktailToDetailsDto(cocktail);
    }

}
