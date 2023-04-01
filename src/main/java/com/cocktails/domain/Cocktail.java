package com.cocktails.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cocktails")
@Getter
@Setter
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL)
    private List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "recipe")
    private String recipe;

}
