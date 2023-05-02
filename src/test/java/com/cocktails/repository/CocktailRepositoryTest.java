package com.cocktails.repository;

import com.cocktails.domain.Cocktail;
import com.cocktails.domain.Glass;
import com.cocktails.domain.enums.PreparationMethod;
import com.cocktails.dto.CocktailSummaryDto;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
public class CocktailRepositoryTest {

    @Autowired
    private CocktailRepository cocktailRepository;

    @Autowired
    private GlassRepository glassRepository;

    @Test
    public void findAllSummaries_ShouldReturnResult() {

        // given
        val cocktail1 = new Cocktail();
        cocktail1.setName("Old Fashioned");
        cocktail1.setPreparationMethod(PreparationMethod.STIRRED);
        val cocktail2 = new Cocktail();
        cocktail2.setName("Gin & Tonic");
        cocktail2.setPreparationMethod(PreparationMethod.STIRRED);
        Glass glass = new Glass();
        glass.setName("Rocks glass");
        glass.setIconName("rocks-glass-icon");
        glassRepository.save(glass);
        cocktail1.setGlass(glass);
        cocktail2.setGlass(glass);
        cocktailRepository.saveAll(Arrays.asList(cocktail1, cocktail2));

        // when
        List<CocktailSummaryDto> summaries = cocktailRepository.findAllSummaries();

        // then
        assertThat(summaries).hasSize(2);

        CocktailSummaryDto summary1 = summaries.get(0);
        assertEquals(cocktail1.getId(), summary1.getId());
        assertEquals(cocktail1.getName(), summary1.getName());
        assertEquals(cocktail1.getPreparationMethod(), summary1.getPreparationMethod());
        assertEquals(glass.getIconName(), summary1.getGlassIcon());

        CocktailSummaryDto summary2 = summaries.get(1);
        assertEquals(cocktail2.getId(), summary2.getId());
        assertEquals(cocktail2.getName(), summary2.getName());
        assertEquals(cocktail2.getPreparationMethod(), summary2.getPreparationMethod());
        assertEquals(glass.getIconName(), summary2.getGlassIcon());
    }
}