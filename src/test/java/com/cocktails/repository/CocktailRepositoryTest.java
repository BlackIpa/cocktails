package com.cocktails.repository;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailSummaryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
public class CocktailRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CocktailRepository cocktailRepository;

    private Cocktail cocktail1;
    private Cocktail cocktail2;

    @BeforeEach
    public void setUp() {
        cocktail1 = new Cocktail();
        cocktail1.setName("Old Fashioned");

        cocktail2 = new Cocktail();
        cocktail2.setName("Gin & Tonic");

        entityManager.persist(cocktail1);
        entityManager.persist(cocktail2);
        entityManager.flush();
    }

    @Test
    public void findAllSummaries_ShouldReturnResult() {

        // when
        List<CocktailSummaryDto> summaries = cocktailRepository.findAllSummaries();

        // then
        assertThat(summaries).isNotNull();
        assertThat(summaries).hasSize(2);

        CocktailSummaryDto summary1 = summaries.get(0);
        assertThat(summary1.getId()).isEqualTo(cocktail1.getId());
        assertThat(summary1.getName()).isEqualTo(cocktail1.getName());

        CocktailSummaryDto summary2 = summaries.get(1);
        assertThat(summary2.getId()).isEqualTo(cocktail2.getId());
        assertThat(summary2.getName()).isEqualTo(cocktail2.getName());
    }
}