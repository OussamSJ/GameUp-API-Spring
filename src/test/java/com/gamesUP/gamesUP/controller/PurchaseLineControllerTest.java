package com.gamesUP.gamesUP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesUP.gamesUP.configuration.NoSecurityConfig;
import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import com.gamesUP.gamesUP.service.PurchaseLineService;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(PurchaseLineController.class)
@Import(NoSecurityConfig.class)
public class PurchaseLineControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private PurchaseLineService service;
    @Autowired private ObjectMapper objectMapper;

    private PurchaseLineDTO dto;

    @BeforeEach
    void setup() {
        dto = new PurchaseLineDTO(1L, 1L, 2L, "GTA", 1, 29.99);
    }

    @Test
    void shouldReturnAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(dto));
        mockMvc.perform(get("/api/purchase-lines"))
                .andExpect(status().isOk());

        verify(service).findAll();
    }

    @Test
    void shouldReturnById() throws Exception {
        when(service.findById(1L)).thenReturn(dto);
        mockMvc.perform(get("/api/purchase-lines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(service).findById(1L);

    }

    @Test
    void shouldCreateLine() throws Exception {
        when(service.create(any())).thenReturn(1L);
        mockMvc.perform(post("/api/purchase-lines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdateLine() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(dto);
        mockMvc.perform(put("/api/purchase-lines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldPatchLine() throws Exception {
        when(service.patch(eq(1L), any())).thenReturn(dto);
        mockMvc.perform(patch("/api/purchase-lines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteLine() throws Exception {
        doNothing().when(service).delete(1L);
        mockMvc.perform(delete("/api/purchase-lines/1"))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }
}
