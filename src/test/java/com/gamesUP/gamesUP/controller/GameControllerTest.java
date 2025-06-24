package com.gamesUP.gamesUP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesUP.gamesUP.configuration.NoSecurityConfig;
import com.gamesUP.gamesUP.dto.GameDTO;
import com.gamesUP.gamesUP.service.GameService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
@Import(NoSecurityConfig.class)
public class GameControllerTest {

    @MockBean
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllGames() throws Exception {
        GameDTO dto = new GameDTO(1L, "Nom", "Genre", "Author", "Pub", "Cat", 2);
        when(gameService.getGames()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Nom"));

        verify(gameService).getGames();
    }

    @Test
    void shouldReturnGameById() throws Exception {
        GameDTO dto = new GameDTO(1L, "Nom", "Genre", "Author", "Pub", "Cat", 2);
        when(gameService.getGameById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Nom"));
        verify(gameService).getGameById(1L);
    }

    @Test
    void shouldCreateGame() throws Exception {
        GameDTO dto = new GameDTO(null, "Nom", "Genre", "Author", "Pub", "Cat", 2);
        when(gameService.create(any())).thenReturn(1L);

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(gameService).create(dto);
    }

    @Test
    void shouldUpdateGame() throws Exception {
        GameDTO dto = new GameDTO(null, "Updated", null, "Author", "Pub", "Cat", 1);
        when(gameService.update(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/games/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.genre").doesNotExist());

        verify(gameService).update(1L,dto);
    }

    @Test
    void shouldPatchGame() throws Exception {
        GameDTO dto = new GameDTO(null, "Patch", null, null, null, null, 3);
        when(gameService.patch(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(patch("/api/games/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(gameService).patch(1L,dto);

    }

    @Test
    void shouldDeleteGame() throws Exception {
        doNothing().when(gameService).delete(1L);

        mockMvc.perform(delete("/api/games/1"))
                .andExpect(status().isNoContent());
        verify(gameService).delete(1L);
    }
}
