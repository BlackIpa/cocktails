package com.cocktails.repository;

import com.cocktails.domain.Cocktail;
import com.cocktails.domain.Glass;
import com.cocktails.dto.CocktailSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlassRepository extends JpaRepository<Glass, Long> {

}
