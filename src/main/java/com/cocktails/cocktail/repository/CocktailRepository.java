package com.cocktails.cocktail.repository;

import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @Query("SELECT new com.cocktails.cocktail.dto.CocktailSummaryDto(c.id, c.name, c.preparationMethod, g.iconName) " +
            "FROM Cocktail c JOIN c.glass g")
    List<CocktailSummaryDto> findAllSummaries();

    @Query("SELECT new com.cocktails.cocktail.dto.CocktailSummaryDto(c.id, c.name, c.preparationMethod, g.iconName) " +
            "FROM Cocktail c JOIN c.glass g " +
            "WHERE LOWER(c.name) LIKE LOWER(concat('%', :cocktailName, '%'))")
    List<CocktailSummaryDto> findByName(@Param("cocktailName") String cocktailName);

    @Query("SELECT c.name FROM Cocktail c WHERE LOWER(c.name) LIKE %:cocktailName%")
    List<String> findByNamePart(@Param("cocktailName") String cocktailName);

}
