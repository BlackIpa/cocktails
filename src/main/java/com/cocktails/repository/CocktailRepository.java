package com.cocktails.repository;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @Query("SELECT new com.cocktails.dto.CocktailSummaryDto(c.id, c.name, c.preparationMethod, g.iconName) " +
            "FROM Cocktail c JOIN c.glass g")
    List<CocktailSummaryDto> findAllSummaries();

    @Query("SELECT new com.cocktails.dto.CocktailSummaryDto(c.id, c.name, c.preparationMethod, g.iconName) " +
            "FROM Cocktail c JOIN c.glass g " +
            "WHERE LOWER(c.name) LIKE LOWER(concat('%', :keyword, '%'))")
    List<CocktailSummaryDto> findByName(@Param("keyword") String keyword);

    @Query("SELECT c.name FROM Cocktail c WHERE LOWER(c.name) LIKE %:keyword%")
    List<String> findByNamePart(@Param("keyword") String keyword);

}
