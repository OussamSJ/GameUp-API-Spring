package com.gamesUP.gamesUP.model;

import com.gamesUP.gamesUP.dto.GameRecommendationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    private List<GameRecommendationDTO> recommendations;
}
