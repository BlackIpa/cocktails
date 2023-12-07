package com.cocktails.cocktail.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "glasses")
@Getter
@Setter
@AllArgsConstructor
public class Glass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon_name")
    private String iconName;

}
