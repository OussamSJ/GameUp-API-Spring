package com.gamesUP.gamesUP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesUP.gamesUP.dto.AvisDTO;
import com.gamesUP.gamesUP.service.AvisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AvisController.class)
public class AvisControllerTest {


    @MockBean
    private AvisService avisService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void shouldReturnAllAvis() throws Exception {
        AvisDTO avis = new AvisDTO(1L, "Cool", 5, 1L, "User", 1L, "Game");
        when(avisService.getAll()).thenReturn(List.of(avis));

        mockMvc.perform(get("/api/avis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].note").value(5))
                .andExpect(jsonPath("$[0].commentaire").value("Cool"));

        verify(avisService).getAll();
    }

    @Test
    void shouldReturnAvisById() throws Exception {
        AvisDTO dto = new AvisDTO(1L, "Comment", 4, 1L, "User", 1L, "Game");
        when(avisService.getById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/avis/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value(4))
                .andExpect(jsonPath("$.commentaire").value("Comment"));

        verify(avisService).getById(1L);
    }

    @Test
    void shouldCreateAvis() throws Exception {
        AvisDTO dto = new AvisDTO(1L, "Comment", 4, 1L, "User", 1L, "Game");
        when(avisService.create(any())).thenReturn(1L);

        mockMvc.perform(post("/api/avis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(avisService).create(dto);
    }
    @Test
    void shouldUpdateAvis() throws Exception {
        AvisDTO input = new AvisDTO(null, "Updated Comment", 4, 1L, "User", 1L, "Game");
        AvisDTO updated = new AvisDTO(1L, "Updated Comment", 4, 1L, "User", 1L, "Game");


        when(avisService.update(eq(1L), any(AvisDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/avis/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.commentaire").value("Updated Comment"))
                .andExpect(jsonPath("$.note").value(4));

        verify(avisService).update(eq(1L), any(AvisDTO.class));
    }
    @Test
    void shouldPatchAvis() throws Exception {
        AvisDTO patchDto = new AvisDTO(null, "Partial Update", 5, null, null, null, null);
        AvisDTO updated = new AvisDTO(1L, "Partial Update", 5, 1L, "User", 1L, "Game");

        when(avisService.patch(eq(1L), any(AvisDTO.class))).thenReturn(updated);

        mockMvc.perform(patch("/api/avis/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentaire").value("Partial Update"))
                .andExpect(jsonPath("$.note").value(5));

        verify(avisService).patch(eq(1L), any(AvisDTO.class));
    }



    @Test
    void shouldDeleteAvis() throws Exception {
        doNothing().when(avisService).delete(1L);

        mockMvc.perform(delete("/api/avis/1"))
                .andExpect(status().isNoContent());
        verify(avisService).delete(1L);
    }
    @Test
    void shouldReturnAvisByUser() throws Exception {
        AvisDTO avis = new AvisDTO(1L, "User Comment", 4, 2L, "Bob", 1L, "Game");
        when(avisService.getAvisByUser(2L)).thenReturn(List.of(avis));

        mockMvc.perform(get("/api/avis/user/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userNom").value("Bob"))
                .andExpect(jsonPath("$[0].commentaire").value("User Comment"));

        verify(avisService).getAvisByUser(2L);
    }

    @Test
    void shouldReturnAvisByGame() throws Exception {
        AvisDTO avis = new AvisDTO(1L, "Game Review", 5, 1L, "Alice", 3L, "Zelda");
        when(avisService.getAvisByGame(3L)).thenReturn(List.of(avis));

        mockMvc.perform(get("/api/avis/game/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameNom").value("Zelda"))
                .andExpect(jsonPath("$[0].note").value(5));

        verify(avisService).getAvisByGame(3L);
    }

}
