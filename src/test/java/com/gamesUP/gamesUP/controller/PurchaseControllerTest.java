package com.gamesUP.gamesUP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesUP.gamesUP.configuration.NoSecurityConfig;
import com.gamesUP.gamesUP.dto.PurchaseDTO;
import com.gamesUP.gamesUP.dto.PurchaseLineDTO;
import com.gamesUP.gamesUP.model.PurchaseStatus;
import com.gamesUP.gamesUP.service.PurchaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PurchaseController.class)
@Import(NoSecurityConfig.class)
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService service;

    @Autowired
    private ObjectMapper mapper;

    private PurchaseDTO dto;

    @BeforeEach
    void setup() {
        dto = new PurchaseDTO(1L, 1L, "John", new Date(), PurchaseStatus.PAID,
                List.of(new PurchaseLineDTO(1L, 1L, 1L, "Game", 1, 10.0)));
    }


    @Test
    void shouldFindAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/purchases"))
                .andExpect(status().isOk());

        verify(service).findAll();
    }
    @Test
    void shouldFindById() throws Exception {
        when(service.findById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/purchases/1"))
                .andExpect(status().isOk());

        verify(service).findById(1L);
    }

    @Test
    void shouldCreate() throws Exception {
        when(service.create(any())).thenReturn(1L);

        mockMvc.perform(post("/api/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(service).create(any());
    }

    @Test
    void shouldUpdate() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(put("/api/purchases/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldPatch() throws Exception {
        when(service.patch(eq(1L), any())).thenReturn(dto);

        mockMvc.perform(patch("/api/purchases/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDelete() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/purchases/1"))
                .andExpect(status().isNoContent());
    }
}
