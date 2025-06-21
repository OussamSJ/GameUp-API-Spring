package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    void testConstructorAndGetters() {
        Category category = new Category();
        category.setId(1L);
        category.setType("Jeux de stratégie");

        assertEquals(1L, category.getId());
        assertEquals("Jeux de stratégie", category.getType());
    }
    @Test
    void testGettersAndSetters() {
        Category category = new Category();

        category.setId(10L);
        category.setType("Puzzle");

        assertEquals(10L, category.getId());
        assertEquals("Puzzle", category.getType());

        // Pour la collection games
        assertNotNull(category.getGames());

        // Modifier la collection games
        category.setGames(null);
        assertNull(category.getGames());
    }


    @Test
    void testAllArgsConstructor() {
        Category category = new Category(2L, "RPG", null);

        assertEquals(2L, category.getId());
        assertEquals("RPG", category.getType());
        assertNull(category.getGames());
    }
}
