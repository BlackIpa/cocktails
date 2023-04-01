package com.cocktails.domain;

import com.cocktails.domain.enums.IngredientType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private IngredientType type;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

}
