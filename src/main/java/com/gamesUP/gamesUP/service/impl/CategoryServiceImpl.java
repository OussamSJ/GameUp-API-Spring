package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Category;
import com.gamesUP.gamesUP.repository.CategoryRepository;
import com.gamesUP.gamesUP.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée"));
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category existing = findById(id);
        existing.setType(category.getType());
        return categoryRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(Math.toIntExact(id))) {
            throw new EntityDontExistException();
        }
        categoryRepository.deleteById(Math.toIntExact(id));
    }

}
