package com.cocktails.repository;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @Query("SELECT new com.cocktails.dto.CocktailSummaryDto(c.id, c.name, c.recipe) FROM Cocktail c")
    List<CocktailSummaryDto> findAllSummaries();
}
