package fr.univamu.iut.apipaniers.beans;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {
    @Test
    public void testBasketGettersAndSetters() {
        int basketId = 1;
        Date confirmationDate = new Date(System.currentTimeMillis());
        boolean confirmed = true;
        String username = "testuser";

        Basket basket = new Basket();
        basket.setBasket_id(basketId);
        basket.setConfirmation_date(confirmationDate);
        basket.setConfirmed(confirmed);
        basket.setUsername(username);

        assertEquals(basketId, basket.getBasket_id());
        assertEquals(confirmationDate, basket.getConfirmation_date());
        assertEquals(confirmed, basket.isConfirmed());
        assertEquals(username, basket.getUsername());
    }

    @Test
    public void testBasketConstructor() {
        int basketId = 1;
        Date confirmationDate = new Date(System.currentTimeMillis());
        boolean confirmed = true;
        String username = "testuser";

        Basket basket = new Basket(basketId, confirmationDate, confirmed, username);

        assertEquals(basketId, basket.getBasket_id());
        assertEquals(confirmationDate, basket.getConfirmation_date());
        assertEquals(confirmed, basket.isConfirmed());
        assertEquals(username, basket.getUsername());
    }
}