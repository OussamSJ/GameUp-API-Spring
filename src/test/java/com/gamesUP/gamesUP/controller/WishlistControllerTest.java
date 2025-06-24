package com.gamesUP.gamesUP.controller;

import com.gamesUP.gamesUP.configuration.NoSecurityConfig;
import com.gamesUP.gamesUP.dto.WishlistGameDTO;
import com.gamesUP.gamesUP.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
@Import(NoSecurityConfig.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishlistService wishlistService;

    @Test
    void shouldGetWishlist() throws Exception {
        WishlistGameDTO dto = new WishlistGameDTO(1L, 1L, "Zelda", "Adventure", "Author", "Publisher", "Action", 1);
        Mockito.when(wishlistService.getWishlist(1L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/wishlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Zelda"));
    }

    @Test
    void shouldAddToWishlist() throws Exception {
        mockMvc.perform(put("/api/wishlist/1/game/2"))
                .andExpect(status().isOk());

        Mockito.verify(wishlistService).addToWishlist(1L, 2L);
    }

    @Test
    void shouldRemoveFromWishlist() throws Exception {
        mockMvc.perform(delete("/api/wishlist/1/game/2"))
                .andExpect(status().isNoContent());

        Mockito.verify(wishlistService).removeFromWishlist(1L, 2L);
    }
}
