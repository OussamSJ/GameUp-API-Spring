package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.model.Author;
import com.gamesUP.gamesUP.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll_shouldReturnAuthors() throws Exception {
        List<Author> authors = List.of(new Author(1L, "Author1", null));
        when(authorService.findAll()).thenReturn(authors);

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Author1")));
    }

    @Test
    void findById_shouldReturnAuthor() throws Exception {
        Author author = new Author(1L, "Author1", null);
        when(authorService.findById(1L)).thenReturn(author);

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Author1")));
    }

    @Test
    void create_shouldReturnCreatedId() throws Exception {
        Author author = new Author(null, "New Author", null);
        when(authorService.create(any())).thenReturn(1L);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    void update_shouldReturnOk() throws Exception {
        Author author = new Author(null, "Updated Author", null);
        when(authorService.findById(1L)).thenReturn(author);
        doNothing().when(authorService).update(eq(1L), any());

        mockMvc.perform(put("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk());

        verify(authorService).update(eq(1L), any());
    }

    @Test
    void delete_shouldReturnOk() throws Exception {
        Author author = new Author(1L, "AuthorToDelete", null);
        when(authorService.findById(1L)).thenReturn(author);
        doNothing().when(authorService).delete(1L);

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isOk());

        verify(authorService).delete(1L);
    }
}
