package com.cocktails.cocktail.repository;

import com.cocktails.cocktail.model.Cocktail;
import com.cocktails.cocktail.model.Glass;
import com.cocktails.cocktail.model.emuns.PreparationMethod;
import com.cocktails.cocktail.dto.CocktailSummaryDto;
import com.cocktails.cocktail.repository.CocktailRepository;
import com.cocktails.cocktail.repository.GlassRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.yaml")
public class CocktailRepositoryTest {

    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    private GlassRepository glassRepository;

    @BeforeEach
    void setUp() {
        // given
        val glass = new Glass();
        glass.setName("Rocks glass");
        glass.setIconName("rocks-glass-icon");
        glassRepository.save(glass);
        val cocktail1 = new Cocktail();
        cocktail1.setName("Old Fashioned");
        cocktail1.setPreparationMethod(PreparationMethod.STIRRED);
        cocktail1.setGlass(glass);
        val cocktail2 = new Cocktail();
        cocktail2.setName("Gin & Tonic");
        cocktail2.setPreparationMethod(PreparationMethod.STIRRED);
        cocktail2.setGlass(glass);
        cocktailRepository.saveAll(Arrays.asList(cocktail1, cocktail2));
    }

    @Test
    public void findAllSummaries_ShouldReturnResult() {

        List<CocktailSummaryDto> summaries = cocktailRepository.findAllSummaries();
        assertNotNull(summaries);
        assertEquals(2, summaries.size());

    }

    @Test
    public void findByName_ShouldReturnResult() {

        List<CocktailSummaryDto> summaries = cocktailRepository.findByName("fas");
        assertNotNull(summaries);
        assertEquals(1, summaries.size());
    }

    @Test
    public void findByNamePart_ShouldReturnResult() {

        List<String> cocktails = cocktailRepository.findByNamePart("fas");
        assertNotNull(cocktails);
        assertEquals(1, cocktails.size());
        assertTrue(cocktails.contains("Old Fashioned"));
    }

}