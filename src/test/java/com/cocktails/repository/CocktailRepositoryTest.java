package com.cocktails.repository;

import com.cocktails.domain.Cocktail;
import com.cocktails.dto.CocktailSummaryDto;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application-test.properties")
public class CocktailRepositoryTest {

    @Autowired
    private CocktailRepository cocktailRepository;

    @Test
    public void findAllSummaries_ShouldReturnResult() {

        // given
        val cocktail1 = new Cocktail();
        cocktail1.setName("Old Fashioned");
        val cocktail2 = new Cocktail();
        cocktail2.setName("Gin & Tonic");
        cocktailRepository.save(cocktail1);
        cocktailRepository.save(cocktail2);

        // when
        List<CocktailSummaryDto> summaries = cocktailRepository.findAllSummaries();

        // then
        assertThat(summaries).isNotNull();
        assertThat(summaries).hasSize(2);
        assertThat(summaries.get(0).getId()).isEqualTo(cocktail1.getId());
        assertThat(summaries.get(0).getName()).isEqualTo(cocktail1.getName());
        assertThat(summaries.get(1).getId()).isEqualTo(cocktail2.getId());
        assertThat(summaries.get(1).getName()).isEqualTo(cocktail2.getName());
    }
}