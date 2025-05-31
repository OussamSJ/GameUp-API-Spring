package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.*;
import com.gamesUP.gamesUP.repository.AuthorRepository;
import com.gamesUP.gamesUP.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;



@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {


    @Autowired
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<Author>();
        authorRepository.findAll().forEach(authors::add);
        return authors;
    }

    @Override
    public Long create(Author author) {

        return authorRepository.save(author).getId();
    }

    @Override
    public Author findById(int id_author) {
        Optional<Author> author = authorRepository.findById(id_author);

        //Si l'auteur est trouvé
        if(author.isPresent()) {
            return author.get();
        }
        //Sinon on renvoie une erreur
        throw new EntityDontExistException();
    }

    @Override
    public void update(int id_author, Author author) {

        author.setId((long) id_author);
        authorRepository.save(author);
    }

    @Override
    public void delete(Long id) {
        if (!authorRepository.existsById(Math.toIntExact(id))) {
            throw new EntityDontExistException();
        }
        authorRepository.deleteById(Math.toIntExact(id));
    }


}
