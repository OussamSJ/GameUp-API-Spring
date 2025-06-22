package com.gamesUP.gamesUP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamesUP.gamesUP.model.Category;
import com.gamesUP.gamesUP.service.CategoryService;
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

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllCategories() throws Exception {
        Category cat = new Category(1L, "RPG",null);
        when(categoryService.findAll()).thenReturn(List.of(cat));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("RPG"));
    }

    @Test
    void shouldReturnCategoryById() throws Exception {
        Category cat = new Category(1L, "Simulation",null);
        when(categoryService.findById(1L)).thenReturn(cat);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Simulation"));
    }

    @Test
    void shouldCreateCategory() throws Exception {
        Category cat = new Category(null, "Shooter",null);
        Category saved = new Category(1L, "Shooter",null);

        when(categoryService.create(any())).thenReturn(saved);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cat)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        Category updated = new Category(1L, "Updated", null);
        when(categoryService.update(eq(1L), any())).thenReturn(updated);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(jsonPath("$.type").value("Updated"))
                .andExpect(status().isOk());


        verify(categoryService).update(eq(1L), any());
    }


    @Test
    void shouldDeleteCategory() throws Exception {

        Category cat = new Category(1L, "ToDelete",null);

        when(categoryService.findById(1L)).thenReturn(cat);
        doNothing().when(categoryService).delete(1L);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());

        verify(categoryService).delete(1L);
    }



}
