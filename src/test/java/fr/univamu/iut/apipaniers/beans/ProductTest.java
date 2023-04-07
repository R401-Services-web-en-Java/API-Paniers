package fr.univamu.iut.apipaniers.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    public void testGettersAndSetters() {
        // Create a new product object
        Product product = new Product();

        // Set values using setters
        product.setName("Apple");
        product.setQuantity_stock(10);
        product.setPrice(0.50f);
        product.setUnit("piece");

        // Assert values using getters
        assertEquals("Apple", product.getName());
        assertEquals(10, product.getQuantity_stock());
        assertEquals(0.50f, product.getPrice(), 0.001f); // Use delta to compare float values
        assertEquals("piece", product.getUnit());
    }

    @Test
    public void testConstructor() {
        // Create a new product object using constructor
        Product product = new Product("Banana", 5, 0.30f, "piece");

        // Assert values using getters
        assertEquals("Banana", product.getName());
        assertEquals(5, product.getQuantity_stock());
        assertEquals(0.30f, product.getPrice(), 0.001f); // Use delta to compare float values
        assertEquals("piece", product.getUnit());
    }

}