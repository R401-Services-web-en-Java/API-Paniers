package fr.univamu.iut.apipaniers.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentTest {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        int basketId = 1;
        String productName = "Product 1";
        int quantity = 10;

        // Act
        Content content = new Content();
        content.setBasket_id(basketId);
        content.setProduct_name(productName);
        content.setQuantity(quantity);

        // Assert
        assertEquals(basketId, content.getBasket_id());
        assertEquals(productName, content.getProduct_name());
        assertEquals(quantity, content.getQuantity());
    }

    @Test
    public void testNotEqualsAndHashCode() {
        // Arrange
        int basketId1 = 1;
        String productName1 = "Product 1";
        int quantity1 = 10;

        int basketId2 = 2;
        String productName2 = "Product 2";
        int quantity2 = 20;

        Content content1 = new Content(basketId1, productName1, quantity1);
        Content content2 = new Content(basketId2, productName2, quantity2);

        // Act
        int hashCode1 = content1.hashCode();
        int hashCode2 = content2.hashCode();

        // Assert
        assertNotEquals(content1, null);
        assertNotEquals(content1, new Object());
        assertNotEquals(content1, content2);
        assertNotEquals(content2, content1);

        assertNotEquals(hashCode1, hashCode2);
    }

}