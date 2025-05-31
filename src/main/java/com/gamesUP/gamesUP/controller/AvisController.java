package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.model.Avis;
import com.gamesUP.gamesUP.service.AvisService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
public class AvisController {

    private final AvisService avisService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Avis> findAll() {
        return avisService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Avis findById(@PathVariable Long id) {
        return avisService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Avis create(@RequestBody Avis avis) {
        return avisService.create(avis);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Avis update(@PathVariable Long id, @RequestBody Avis avis) {
        return avisService.update(id, avis);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void patchAvis(@PathVariable("id") Long id, @RequestBody Avis partialAvis) {
        avisService.patch(id, partialAvis);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        avisService.delete(id);
    }

    @GetMapping("/game/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Avis> getAvisByGameId(@PathVariable Long gameId) {
        return avisService.getAvisByGameId(gameId);
    }
}
