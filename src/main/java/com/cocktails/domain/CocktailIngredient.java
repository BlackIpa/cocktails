package com.cocktails.domain;

import com.cocktails.domain.enums.Unit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cocktail_ingredients")
@Getter
@Setter
public class CocktailIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    @JsonIgnore
    private Cocktail cocktail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    @JsonIgnore
    private Ingredient ingredient;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private Unit unit;

}
