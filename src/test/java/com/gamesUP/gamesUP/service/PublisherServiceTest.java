package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Publisher;
import com.gamesUP.gamesUP.repository.PublisherRepository;
import com.gamesUP.gamesUP.service.impl.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllPublishers() {
        Publisher pub = new Publisher(1L, "Ubisoft", null);
        when(publisherRepository.findAll()).thenReturn(List.of(pub));

        List<Publisher> publishers = publisherService.findAll();

        assertThat(publishers).hasSize(1);
        assertThat(publishers.get(0).getName()).isEqualTo("Ubisoft");

        verify(publisherRepository).findAll();

    }

    @Test
    void shouldReturnPublisherById() {
        Publisher pub = new Publisher(1L, "EA", null);
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(pub));

        Publisher result = publisherService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("EA");

        verify(publisherRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenPublisherNotFound() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> publisherService.findById(1L))
                .isInstanceOf(EntityDontExistException.class)
                .hasMessageContaining("Publisher not found");

        verify(publisherRepository).findById(1L);

    }

    @Test
    void shouldCreatePublisher() {
        Publisher pub = new Publisher(null, "Square Enix", null);
        Publisher saved = new Publisher(1L, "Square Enix", null);
        when(publisherRepository.save(pub)).thenReturn(saved);

        Long id = publisherService.create(pub);

        assertThat(id).isEqualTo(1L);

        verify(publisherRepository).save(pub);
    }

    @Test
    void shouldUpdatePublisher() {
        Publisher existing = new Publisher(1L, "Old Name", null);
        Publisher update = new Publisher(null, "New Name", null);

        when(publisherRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(publisherRepository.save(existing)).thenReturn(existing);

        Publisher result = publisherService.update(1L, update);

        assertThat(result.getName()).isEqualTo("New Name");
        verify(publisherRepository).findById(1L);
        verify(publisherRepository).save(existing);
    }

    @Test
    void shouldDeletePublisher() {
        Publisher pub = new Publisher(1L, "Namco", null);
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(pub));
        doNothing().when(publisherRepository).delete(pub);

        publisherService.delete(1L);

        verify(publisherRepository).delete(pub);
    }
}
