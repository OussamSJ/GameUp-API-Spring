package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.dto.GameDTO;
import com.gamesUP.gamesUP.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GameDTO> findAll() {
        return gameService.getGames();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO findById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid GameDTO gameDTO) {
        return gameService.create(gameDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GameDTO update(@PathVariable Long id, @RequestBody @Valid GameDTO gameDTO) {
        return gameService.update(id, gameDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        gameService.delete(id);
    }
}
