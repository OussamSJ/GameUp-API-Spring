package com.gamesUP.gamesUP.service.impl;

import com.gamesUP.gamesUP.dto.GameRecommendationDTO;
import com.gamesUP.gamesUP.dto.UserRecommendationRequest;
import com.gamesUP.gamesUP.model.Recommendation;
import com.gamesUP.gamesUP.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RestTemplate restTemplate;

    @Override
    public List<GameRecommendationDTO> getRecommendations(UserRecommendationRequest request) {
        String url = "http://localhost:8000/recommendations/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserRecommendationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Recommendation> response = restTemplate
                .postForEntity(url, entity, Recommendation.class);

        return response.getBody().getRecommendations();
    }

}
