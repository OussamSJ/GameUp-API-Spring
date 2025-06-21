package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

public class InventoryTest {

    @Test
    void testGettersAndSetters() {
        Inventory inventory = new Inventory();

        inventory.setId(1L);
        inventory.setName("Main Inventory");

        InventoryLine line1 = new InventoryLine();
        line1.setId(10L);
        line1.setQuantity(5);

        InventoryLine line2 = new InventoryLine();
        line2.setId(20L);
        line2.setQuantity(10);

        inventory.setStock(new HashSet<>());
        inventory.getStock().add(line1);
        inventory.getStock().add(line2);

        assertEquals(1L, inventory.getId());
        assertEquals("Main Inventory", inventory.getName());
        assertEquals(2, inventory.getStock().size());
        assertTrue(inventory.getStock().contains(line1));
        assertTrue(inventory.getStock().contains(line2));
    }

    @Test
    void testAllArgsConstructor() {
        InventoryLine line = new InventoryLine();
        line.setId(5L);
        line.setQuantity(2);

        Inventory inventory = new Inventory(2L, "Secondary Inventory", new HashSet<>());
        inventory.getStock().add(line);

        assertEquals(2L, inventory.getId());
        assertEquals("Secondary Inventory", inventory.getName());
        assertEquals(1, inventory.getStock().size());
    }
}
