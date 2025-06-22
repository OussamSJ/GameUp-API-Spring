package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Category;
import com.gamesUP.gamesUP.repository.CategoryRepository;
import com.gamesUP.gamesUP.service.impl.CategoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnCategories() {
        Category cat = new Category(1L, "Action",null);
        when(categoryRepository.findAll()).thenReturn(List.of(cat));

        List<Category> result = categoryService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getType()).isEqualTo("Action");
        verify(categoryRepository).findAll();
    }

    @Test
    void findById_whenFound_shouldReturnCategory() {
        Category cat = new Category(1L, "Adventure",null);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));

        Category result = categoryService.findById(1L);

        assertThat(result.getType()).isEqualTo("Adventure");
        verify(categoryRepository).findById(1L);
    }

    @Test
    void findById_whenNotFound_shouldThrow() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class);
        verify(categoryRepository).findById(1L);
    }

    @Test
    void create_shouldSaveCategory() {
        Category cat = new Category(null, "Horror",null);
        when(categoryRepository.save(cat)).thenReturn(new Category(1L, "Horror",null));

        Category saved = categoryService.create(cat);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getId()).isEqualTo(1L);
        verify(categoryRepository).save(cat);
    }

    @Test
    void update_whenFound_shouldUpdate() {
        Category existing = new Category(1L, "Old",null);
        Category updated = new Category(null, "New",null);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(existing)).thenReturn(existing);

        Category result = categoryService.update(1L, updated);

        assertThat(result.getType()).isEqualTo("New");
        verify(categoryRepository).save(existing);
    }

    @Test
    void delete_whenExists_shouldDelete() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.delete(1L);

        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void delete_whenNotExists_shouldThrow() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> categoryService.delete(1L))
                .isInstanceOf(EntityDontExistException.class);
    }
}
