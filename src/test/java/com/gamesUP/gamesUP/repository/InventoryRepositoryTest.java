package com.gamesUP.gamesUP.repository;

import com.gamesUP.gamesUP.model.Inventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    @DisplayName("Save and retrieve an Inventory entity")
    void testSaveAndFind() {
        Inventory inventory = new Inventory();
        inventory.setName("Test Inventory");

        Inventory saved = inventoryRepository.save(inventory);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Test Inventory");

        Inventory found = inventoryRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Inventory");
    }
}
