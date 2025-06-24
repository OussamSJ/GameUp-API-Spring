package com.gamesUP.gamesUP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesUP.gamesUP.configuration.NoSecurityConfig;
import com.gamesUP.gamesUP.model.Inventory;
import com.gamesUP.gamesUP.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
@Import(NoSecurityConfig.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllInventories() throws Exception {
        Inventory inv = new Inventory(1L, "Stock", null);
        when(inventoryService.findAll()).thenReturn(List.of(inv));

        mockMvc.perform(get("/api/inventories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Stock"));

        verify(inventoryService).findAll();
    }

    @Test
    void shouldReturnInventoryById() throws Exception {
        Inventory inv = new Inventory(1L, "Backup", null);
        when(inventoryService.findById(1L)).thenReturn(inv);

        mockMvc.perform(get("/api/inventories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Backup"));

        verify(inventoryService).findById(1L);
    }

    @Test
    void shouldCreateInventory() throws Exception {
        Inventory inv = new Inventory(null, "New", null);
        when(inventoryService.create(any())).thenReturn(1L);

        mockMvc.perform(post("/api/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inv)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void shouldUpdateInventory() throws Exception {
        Inventory updated = new Inventory(null, "Updated", null);
        Inventory result = new Inventory(1L, "Updated", null);
        when(inventoryService.update(eq(1L), any())).thenReturn(result);

        mockMvc.perform(put("/api/inventories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));

    }

    @Test
    void shouldDeleteInventory() throws Exception {
        doNothing().when(inventoryService).delete(1L);

        mockMvc.perform(delete("/api/inventories/1"))
                .andExpect(status().isNoContent());

        verify(inventoryService).delete(1L);
    }
}
