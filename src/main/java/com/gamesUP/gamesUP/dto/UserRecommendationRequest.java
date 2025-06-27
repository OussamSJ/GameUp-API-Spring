package com.gamesUP.gamesUP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRecommendationRequest {

    private int user_id;
    private int game_id;
    private float rating;
}
