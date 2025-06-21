package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryLineTest {

    @Test
    void testGettersAndSetters() {
        InventoryLine line = new InventoryLine();

        line.setId(1L);
        line.setQuantity(10);

        Inventory inventory = new Inventory();
        inventory.setId(100L);
        inventory.setName("Main Inventory");

        Game game = new Game();
        game.setId(200L);
        game.setNom("Test Game");
        game.setGenre("Action");

        line.setInventory(inventory);
        line.setGame(game);

        assertEquals(1L, line.getId());
        assertEquals(10, line.getQuantity());
        assertEquals(inventory, line.getInventory());
        assertEquals(game, line.getGame());
    }

    @Test
    void testAllArgsConstructor() {
        Inventory inventory = new Inventory(10L, "Inventory A", null);
        Game game = new Game(20L, "Game A", null,null, null, null, 1);

        InventoryLine line = new InventoryLine(5L, inventory, game, 50);

        assertEquals(5L, line.getId());
        assertEquals(inventory, line.getInventory());
        assertEquals(game, line.getGame());
        assertEquals(50, line.getQuantity());
    }
}
