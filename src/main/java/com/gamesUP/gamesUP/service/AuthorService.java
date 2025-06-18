package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    public Long create(Author author);

    Author findById(Long id_author);

    void update(Long idAuthor, Author author);

    void delete(Long id_author);
}
