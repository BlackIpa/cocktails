package com.cocktails.cocktail.model;

import com.cocktails.cocktail.model.emuns.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ingredients")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<CocktailIngredient> cocktailIngredients = new ArrayList<>();

}
