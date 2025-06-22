package com.gamesUP.gamesUP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesUP.gamesUP.dto.InventoryLineDTO;
import com.gamesUP.gamesUP.service.InventoryLineService;
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

@WebMvcTest(InventoryLineController.class)
public class InventoryLineControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private InventoryLineService service;

    @Test
    void shouldReturnAllInventoryLines() throws Exception {
        InventoryLineDTO dto = new InventoryLineDTO(1L, 1L, "Main", 1L, "Game", 5);
        when(service.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/inventory-lines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameName").value("Game"))
                .andExpect(jsonPath("$[0].quantity").value(5));

        verify(service).findAll();
    }

    @Test
    void shouldReturnInventoryLineById() throws Exception {
        InventoryLineDTO dto = new InventoryLineDTO(1L, 10L, "Inv A", 20L, "Game A", 5);
        when(service.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/inventory-lines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.inventoryName").value("Inv A"))
                .andExpect(jsonPath("$.gameName").value("Game A"));

        verify(service).findById(1L);

    }


    @Test
    void shouldCreateInventoryLine() throws Exception {
        InventoryLineDTO dto = new InventoryLineDTO(null, 1L, null, 1L, null, 4);
        when(service.create(any())).thenReturn(1L);

        mockMvc.perform(post("/api/inventory-lines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(service).create(dto);
    }

    @Test
    void shouldUpdateInventoryLine() throws Exception {
        InventoryLineDTO input = new InventoryLineDTO(1L, 10L, "Inv A", 20L, "Game A", 8);
        when(service.update(eq(1L), any())).thenReturn(input);

        mockMvc.perform(put("/api/inventory-lines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(8));

        verify(service).update(eq(1L), any());
    }

    @Test
    void shouldPatchInventoryLine() throws Exception {
        InventoryLineDTO input = new InventoryLineDTO(null, null, null, null, null, 12);
        InventoryLineDTO result = new InventoryLineDTO(1L, 10L, "Inv A", 20L, "Game A", 12);

        when(service.patch(eq(1L), any())).thenReturn(result);

        mockMvc.perform(patch("/api/inventory-lines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(12));

        verify(service).patch(eq(1L), any());
    }

    @Test
    void shouldDeleteInventoryLine() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/inventory-lines/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    @Test
    void shouldReturnInventoryLinesByInventoryId() throws Exception {
        InventoryLineDTO dto = new InventoryLineDTO(1L, 100L, "Stock A", 200L, "Game X", 3);
        when(service.getByInventoryId(100L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/inventory-lines/inventory/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].inventoryId").value(100L))
                .andExpect(jsonPath("$[0].gameName").value("Game X"));

        verify(service).getByInventoryId(100L);
    }
    @Test
    void shouldReturnInventoryLinesByGameId() throws Exception {
        InventoryLineDTO dto = new InventoryLineDTO(1L, 101L, "Main", 202L, "Tetris", 4);
        when(service.getByGameId(202L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/inventory-lines/game/202"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameId").value(202L))
                .andExpect(jsonPath("$[0].inventoryName").value("Main"));

        verify(service).getByGameId(202L);
    }

}
