package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.dto.GameRecommendationDTO;
import com.gamesUP.gamesUP.dto.UserRecommendationRequest;

import java.util.List;

public interface RecommendationService {
    List<GameRecommendationDTO> getRecommendations(UserRecommendationRequest request);
}
