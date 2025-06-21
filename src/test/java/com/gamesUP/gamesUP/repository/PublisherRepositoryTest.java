package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Publisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PublisherRepositoryTest {

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    @DisplayName("Should save and retrieve Publisher")
    void shouldSaveAndRetrievePublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Ubisoft");

        Publisher saved = publisherRepository.save(publisher);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Ubisoft");

        Optional<Publisher> found = publisherRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Ubisoft");
    }

    @Test
    @DisplayName("Should update Publisher name")
    void shouldUpdatePublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("Initial Name");
        Publisher saved = publisherRepository.save(publisher);

        saved.setName("Updated Name");
        Publisher updated = publisherRepository.save(saved);

        assertThat(updated.getName()).isEqualTo("Updated Name");
    }

    @Test
    @DisplayName("Should delete Publisher")
    void shouldDeletePublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("ToDelete");
        Publisher saved = publisherRepository.save(publisher);

        publisherRepository.deleteById(saved.getId());

        assertThat(publisherRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    @DisplayName("Should find by name")
    void shouldFindByName() {
        Publisher publisher = new Publisher();
        publisher.setName("publisher");
        publisherRepository.save(publisher);

        Optional<Publisher> result = publisherRepository.findByName("publisher");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("publisher");
    }
}
