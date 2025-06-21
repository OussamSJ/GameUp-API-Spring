package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthorTest {

    @Test
    public void testAuthorConstructorAndGetters() {
        Author author = new Author(1L, "John", new HashSet<>());

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("John");
        assertThat(author.getGames()).isEmpty();
    }

    @Test
    public void testSetters() {
        Author author = new Author();
        author.setId(2L);
        author.setName("Jane");

        assertThat(author.getId()).isEqualTo(2L);
        assertThat(author.getName()).isEqualTo("Jane");
    }
}
