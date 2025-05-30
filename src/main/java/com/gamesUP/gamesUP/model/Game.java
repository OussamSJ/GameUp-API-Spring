package com.gamesUP.gamesUP.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    private String genre;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Avis> avis = new HashSet<>();

    private int numEdition;


}
