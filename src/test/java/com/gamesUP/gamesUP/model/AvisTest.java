package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvisTest {

    @Test
    void testAvisConstructorAndGetters() {
        // Création des entités associées
        User user = new User();
        user.setId(1L);
        user.setNom("John Doe");

        Game game = new Game();
        game.setId(2L);
        game.setNom("Test Game");

        // Création d'un avis
        Avis avis = new Avis(10L, "Très bon jeu", 5, user, game);

        // Vérification des champs
        assertEquals(10L, avis.getId());
        assertEquals("Très bon jeu", avis.getCommentaire());
        assertEquals(5, avis.getNote());
        assertEquals(user, avis.getUser());
        assertEquals(game, avis.getGame());
    }

    @Test
    void testSetters() {
        Avis avis = new Avis();

        User user = new User();
        user.setId(3L);

        Game game = new Game();
        game.setId(4L);

        avis.setId(100L);
        avis.setCommentaire("Mauvais");
        avis.setNote(2);
        avis.setUser(user);
        avis.setGame(game);

        assertEquals(100L, avis.getId());
        assertEquals("Mauvais", avis.getCommentaire());
        assertEquals(2, avis.getNote());
        assertEquals(3L, avis.getUser().getId());
        assertEquals(4L, avis.getGame().getId());
    }
}
