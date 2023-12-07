package com.cocktails.cocktail.model;

import com.cocktails.cocktail.model.emuns.PreparationMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cocktails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cocktail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "cocktail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "preparation_method")
    private PreparationMethod preparationMethod;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "glass_id")
    private Glass glass;

}
