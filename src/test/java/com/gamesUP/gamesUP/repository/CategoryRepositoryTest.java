package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Test save and findById")
    void testSaveAndFind() {
        Category saved = categoryRepository.save(new Category(null, "Action", null));

        Optional<Category> found = categoryRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getType()).isEqualTo("Action");
    }

    @Test
    @DisplayName("Test existsById returns true")
    void testExistsById() {
        Category category = categoryRepository.save(new Category(null, "Aventure", null));
        boolean exists = categoryRepository.existsById(category.getId());
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Test deleteById")
    void testDeleteById() {
        Category category = categoryRepository.save(new Category(null, "Stratégie", null));
        categoryRepository.deleteById(category.getId());

        Optional<Category> found = categoryRepository.findById(category.getId());
        assertThat(found).isNotPresent();
    }
}
