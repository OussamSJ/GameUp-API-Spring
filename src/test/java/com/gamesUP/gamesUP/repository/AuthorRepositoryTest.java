package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    @DisplayName("should save and retrieve an Author")
    public void testSaveAndFindById() {
        Author author = new Author();
        author.setName("Test Author");

        Author saved = authorRepository.save(author);

        Optional<Author> retrieved = authorRepository.findById(saved.getId());

        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getName()).isEqualTo("Test Author");
    }

    @Test
    @DisplayName("should save author with empty game set")
    public void testSaveAuthorWithEmptyGameSet() {
        Author author = new Author(null, "No Games Author", new HashSet<>());

        Author saved = authorRepository.save(author);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getGames()).isEmpty();
    }
    @Test
    public void testFindByName() {
        Author author = new Author();
        author.setName("Special Name");
        authorRepository.save(author);

        Optional<Author> found = authorRepository.findByName("Special Name");
        assertThat(found).isPresent();
    }

}
