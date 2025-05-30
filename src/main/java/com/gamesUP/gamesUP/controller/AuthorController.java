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
    private AuthorService authorService;

    @GetMapping
    @ResponseStatus(code= HttpStatus.OK)
    public List<Author> findAll(){

        return authorService.findAll();

    }
}
