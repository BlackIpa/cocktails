package com.cocktails.service;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailDetailsDto;
import com.cocktails.dto.CocktailSummaryDto;
import com.cocktails.repository.CocktailRepository;
import com.cocktails.service.mapper.CocktailMapper;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository cocktailRepository;
    private final CocktailMapper cocktailMapper;

    public CocktailServiceImpl(CocktailRepository cocktailRepository, CocktailMapper cocktailMapper) {
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

    public List<String> findByNamePart(String keyword) {
        if (keyword == null || keyword.length() < 3) {
            return Collections.emptyList();
        }
        return cocktailRepository.findByNamePart(keyword);
    }

    @Override
    public List<CocktailSummaryDto> findByName(String keyword) {
        if (keyword == null || keyword.length() < 3) {
            return Collections.emptyList();
        }
        return cocktailRepository.findByName(keyword);
    }

}
