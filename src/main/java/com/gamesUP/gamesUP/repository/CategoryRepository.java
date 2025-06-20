package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByType(String type);
}
