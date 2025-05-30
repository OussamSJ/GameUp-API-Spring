package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.model.Author;
import com.gamesUP.gamesUP.repository.AuthorRepository;
import com.gamesUP.gamesUP.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorService {


    @Autowired
    AuthorRepository authorRepository;

    /**
     * @return
     */
    @Override
    public List<Author> findAll() {

        List<Author> authors = new ArrayList<Author>();

        authorRepository.findAll().forEach(authors::add);

        return authors;
    }
}
