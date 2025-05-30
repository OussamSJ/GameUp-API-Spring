package com.gamesUP.gamesUP.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Inventory inventory;

    @ManyToOne(optional = false)
    private Game game;

    private int quantity;
}