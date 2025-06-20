package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AuthorRepository extends JpaRepository<Author,Long> {
    Optional<Author> findByName(String name);
}
