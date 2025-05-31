package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.model.Author;
import com.gamesUP.gamesUP.service.AuthorService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    @GetMapping
    @ResponseStatus(code= HttpStatus.OK)
    public List<Author> findAll(){
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Author findById(@PathVariable("id") int id_author){
        return authorService.findById(id_author);
    }

    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED)
    private Long create(@RequestBody Author author) {
        return authorService.create(author);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void modifier(@PathVariable("id") int id_author, @RequestBody Author author) {
        authorService.findById(id_author);
        authorService.update(id_author,author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.OK)
    public void delete( @PathVariable("id") int id_author) {
        //Vérifier si l'author existe puis supprimer
        authorService.findById(id_author);
        Author author = authorService.findById(id_author);
        authorService.delete(author.getId());
    }

}
