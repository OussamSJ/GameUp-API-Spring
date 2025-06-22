package com.gamesUP.gamesUP.service;

import com.gamesUP.gamesUP.exception.EntityDontExistException;
import com.gamesUP.gamesUP.model.Inventory;
import com.gamesUP.gamesUP.repository.InventoryRepository;
import com.gamesUP.gamesUP.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllInventories() {
        Inventory inv = new Inventory(1L, "Main", null);
        when(inventoryRepository.findAll()).thenReturn(List.of(inv));

        List<Inventory> list = inventoryService.findAll();

        assertThat(list).hasSize(1);
        assertThat(list.get(0).getName()).isEqualTo("Main");

        verify(inventoryRepository).findAll();
    }

    @Test
    void shouldReturnInventoryById() {
        Inventory inv = new Inventory(1L, "Backup", null);
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inv));

        Inventory found = inventoryService.findById(1L);

        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getName()).isEqualTo("Backup");

        verify(inventoryRepository).findById(1L);

    }

    @Test
    void shouldThrowWhenInventoryNotFound() {
        when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> inventoryService.findById(1L))
                .isInstanceOf(EntityDontExistException.class)
                .hasMessageContaining("Inventory not found");
    }

    @Test
    void shouldCreateInventory() {
        Inventory inv = new Inventory(null, "Store", null);
        Inventory saved = new Inventory(1L, "Store", null);
        when(inventoryRepository.save(inv)).thenReturn(saved);

        Long id = inventoryService.create(inv);

        assertThat(id).isEqualTo(1L);


    }

    @Test
    void shouldUpdateInventory() {
        Inventory existing = new Inventory(1L, "Old", null);
        Inventory updated = new Inventory(null, "New", null);

        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(inventoryRepository.save(any())).thenReturn(existing);

        Inventory result = inventoryService.update(1L, updated);

        assertThat(result.getName()).isEqualTo("New");

        verify(inventoryRepository).findById(1L);
        verify(inventoryRepository).save(existing);
    }

    @Test
    void shouldDeleteInventory() {
        Inventory inv = new Inventory(1L, "Main", null);
        when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inv));
        doNothing().when(inventoryRepository).delete(inv);

        inventoryService.delete(1L);

        verify(inventoryRepository).delete(inv);
    }
}
