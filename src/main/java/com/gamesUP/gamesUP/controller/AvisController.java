package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.dto.AvisDTO;
import com.gamesUP.gamesUP.service.AvisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
public class AvisController {

    private final AvisService avisService;

    @GetMapping
    public List<AvisDTO> findAll() {
        return avisService.getAll();
    }

    @GetMapping("/{id}")
    public AvisDTO findById(@PathVariable Long id) {
        return avisService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody @Valid AvisDTO dto) {
        return avisService.create(dto);
    }

    @PutMapping("/{id}")
    public AvisDTO update(@PathVariable Long id, @RequestBody @Valid AvisDTO dto) {
        return avisService.update(id, dto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AvisDTO patchAvis(@PathVariable Long id, @RequestBody AvisDTO dto) {
        return avisService.patch(id, dto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        avisService.delete(id);
    }

    @GetMapping("/user/{userId}")
    public List<AvisDTO> getAvisByUser(@PathVariable Long userId) {
        return avisService.getAvisByUser(userId);
    }
    @GetMapping("/game/{gameId}")
    public List<AvisDTO> getAvisByGame(@PathVariable Long gameId) {
        return avisService.getAvisByGame(gameId);
    }

}

