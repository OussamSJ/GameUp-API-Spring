package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.dto.GameRecommendationDTO;
import com.gamesUP.gamesUP.dto.UserRecommendationRequest;
import com.gamesUP.gamesUP.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService service) {
        this.recommendationService = service;
    }

    @PostMapping
    public ResponseEntity<List<GameRecommendationDTO>> getRecommendations(@RequestBody UserRecommendationRequest request) {
        List<GameRecommendationDTO> recs = recommendationService.getRecommendations(request);
        return ResponseEntity.ok(recs);
    }
}
