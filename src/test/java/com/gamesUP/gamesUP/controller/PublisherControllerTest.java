package com.gamesUP.gamesUP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesUP.gamesUP.configuration.NoSecurityConfig;
import com.gamesUP.gamesUP.model.Publisher;
import com.gamesUP.gamesUP.service.PublisherService;
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

@WebMvcTest(PublisherController.class)
@Import(NoSecurityConfig.class)
class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllPublishers() throws Exception {
        Publisher pub = new Publisher(1L, "Sony", null);
        when(publisherService.findAll()).thenReturn(List.of(pub));

        mockMvc.perform(get("/api/publishers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sony"));
    }

    @Test
    void shouldReturnPublisherById() throws Exception {
        Publisher pub = new Publisher(1L, "Activision", null);
        when(publisherService.findById(1L)).thenReturn(pub);

        mockMvc.perform(get("/api/publishers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Activision"));
    }

    @Test
    void shouldCreatePublisher() throws Exception {
        Publisher pub = new Publisher(null, "Valve", null);
        when(publisherService.create(any())).thenReturn(1L);

        mockMvc.perform(post("/api/publishers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pub)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

    }

    @Test
    void shouldUpdatePublisher() throws Exception {
        Publisher input = new Publisher(null, "Blizzard", null);
        Publisher updated = new Publisher(1L, "Blizzard", null);
        when(publisherService.update(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/api/publishers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Blizzard"));

    }

    @Test
    void shouldDeletePublisher() throws Exception {
        doNothing().when(publisherService).delete(1L);

        mockMvc.perform(delete("/api/publishers/1"))
                .andExpect(status().isNoContent());

        verify(publisherService).delete(1L);
    }
}
