package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Author;
import com.gamesUP.gamesUP.repository.AuthorRepository;
import com.gamesUP.gamesUP.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnList() {
        List<Author> authors = List.of(new Author(1L, "Author1", null));
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.findAll();

        assertThat(result).hasSize(1);
        verify(authorRepository).findAll();
    }

    @Test
    void findById_whenFound_shouldReturnAuthor() {
        Author author = new Author(1L, "Author1", null);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author result = authorService.findById(1L);

        assertThat(result.getName()).isEqualTo("Author1");
    }

    @Test
    void findById_whenNotFound_shouldThrow() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findById(999L))
                .isInstanceOf(EntityDontExistException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void create_shouldSaveAuthor() {
        Author author = new Author(null, "New Author", null);
        Author saved = new Author(1L, "New Author", null);
        when(authorRepository.save(author)).thenReturn(saved);

        Long id = authorService.create(author);

        assertThat(id).isEqualTo(1L);
        verify(authorRepository).save(author);
    }

    /*@Test
    void update_whenFound_shouldSave() {
        Author existing = new Author(1L, "Old Name", null);
        Author update = new Author(null, "New Name", null);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(authorRepository.save(any())).thenReturn(existing);

        authorService.update(1L, update);

        assertThat(existing.getName()).isEqualTo("New Name");
        verify(authorRepository).save(existing);
    }

    @Test
    void update_whenNotFound_shouldThrow() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.update(1L, new Author()))
                .isInstanceOf(EntityDontExistException.class);
    }*/

    /*@Test
    void delete_whenFound_shouldDelete() {
        Author author = new Author(1L, "Author", null);
        author  =   authorRepository.save(author);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        doNothing().when(authorRepository).deleteById(1L);

        authorService.delete(1L);

        verify(authorRepository).deleteById(1L);
    }

    @Test
    void delete_whenNotFound_shouldThrow() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.delete(1L))
                .isInstanceOf(EntityDontExistException.class);
    }*/
}
