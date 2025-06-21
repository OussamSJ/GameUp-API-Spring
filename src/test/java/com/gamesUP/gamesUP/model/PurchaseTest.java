package com.gamesUP.gamesUP.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseTest {

    @Test
    void testConstructorAndGettersSetters() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setNom("Alice");

        PurchaseLine line1 = new PurchaseLine();
        line1.setId(1L);
        PurchaseLine line2 = new PurchaseLine();
        line2.setId(2L);

        ArrayList<PurchaseLine> lines = new ArrayList<>();
        lines.add(line1);
        lines.add(line2);

        Date date = new Date();

        Purchase purchase = new Purchase();
        purchase.setId(100L);
        purchase.setUser(user);
        purchase.setLines(lines);
        purchase.setDate(date);
        purchase.setStatus(PurchaseStatus.PAID);

        // Assert
        assertThat(purchase.getId()).isEqualTo(100L);
        assertThat(purchase.getUser()).isEqualTo(user);
        assertThat(purchase.getLines()).hasSize(2);
        assertThat(purchase.getDate()).isEqualTo(date);
        assertThat(purchase.getStatus()).isEqualTo(PurchaseStatus.PAID);
    }

    @Test
    void testAllArgsConstructor() {
        User user = new User();
        user.setId(2L);

        Date date = new Date();
        Purchase purchase = new Purchase(2L, user, new ArrayList<>(), date, PurchaseStatus.ARCHIVED);

        assertThat(purchase.getId()).isEqualTo(2L);
        assertThat(purchase.getUser()).isEqualTo(user);
        assertThat(purchase.getLines()).isEmpty();
        assertThat(purchase.getDate()).isEqualTo(date);
        assertThat(purchase.getStatus()).isEqualTo(PurchaseStatus.ARCHIVED);
    }
}
