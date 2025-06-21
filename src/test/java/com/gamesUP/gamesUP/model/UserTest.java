package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    void shouldCreateUserWithFields() {
        User user = new User(1L, "Alice", new HashSet<>());

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getNom()).isEqualTo("Alice");
        assertThat(user.getAvis()).isEmpty();
    }

    @Test
    void shouldSetAndGetUserFields() {
        User user = new User();
        user.setId(2L);
        user.setNom("Bob");

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getNom()).isEqualTo("Bob");
    }

    @Test
    void shouldAddAvisToUser() {
        User user = new User();
        user.setNom("Charlie");

        Avis avis = new Avis();
        avis.setCommentaire("Excellent !");
        avis.setNote(5);
        avis.setUser(user);

        user.getAvis().add(avis);

        assertThat(user.getAvis()).contains(avis);
    }
}
