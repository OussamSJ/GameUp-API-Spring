package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void testGettersAndSetters() {
        Game game = new Game();

        game.setId(100L);
        game.setNom("Test Game");

        Author author = new Author();
        author.setId(1L);
        author.setName("Author 1");

        Category category = new Category();
        category.setId(2L);
        category.setType("Category 1");

        Publisher publisher = new Publisher();
        publisher.setId(3L);
        publisher.setName("Publisher 1");

        game.setAuthor(author);
        game.setCategory(category);
        game.setPublisher(publisher);
        game.setGenre("Action");
        game.setNumEdition(5);

        assertEquals(100L, game.getId());
        assertEquals("Test Game", game.getNom());
        assertEquals(author, game.getAuthor());
        assertEquals(category, game.getCategory());
        assertEquals(publisher, game.getPublisher());
        assertEquals("Action", game.getGenre());
        assertEquals(5, game.getNumEdition());
    }

    @Test
    void testAllArgsConstructor() {
        Author author = new Author(1L, "Author", null);
        Category category = new Category(2L, "Category", null);
        Publisher publisher = new Publisher(3L, "Publisher",null);

        Game game = new Game(10L, "GameName", author, "Genre", category, publisher, 3);

        assertEquals(10L, game.getId());
        assertEquals("GameName", game.getNom());
        assertEquals(author, game.getAuthor());
        assertEquals("Genre", game.getGenre());
        assertEquals(category, game.getCategory());
        assertEquals(publisher, game.getPublisher());
        assertEquals(3, game.getNumEdition());
    }
}
