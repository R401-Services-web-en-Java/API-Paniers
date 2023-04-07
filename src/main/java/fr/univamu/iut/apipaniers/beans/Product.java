package fr.univamu.iut.apipaniers.beans;

/**
 * The Products class represents a product with its properties such as name, quantity in stock, price, and unit.
 */
public class Product {
    protected String name; // The name of the product
    protected int quantity_stock; // The quantity of the product in stock
    protected float price; // The price of the product
    protected String unit; // The unit of measurement for the product

    /**
     * Default constructor for Products class.
     */
    public Product() {
    }

    /**
     * Constructor for Products class with parameters.
     *
     * @param name           The name of the product.
     * @param quantity_stock The quantity of the product in stock.
     * @param price          The price of the product.
     * @param unit           The unit of measurement for the product.
     */
    public Product(String name, int quantity_stock, float price, String unit) {
        this.name = name;
        this.quantity_stock = quantity_stock;
        this.price = price;
        this.unit = unit;
    }

    /**
     * Gets the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the quantity of the product in stock.
     *
     * @return The quantity of the product in stock.
     */
    public int getQuantity_stock() {
        return quantity_stock;
    }

    /**
     * Sets the quantity of the product in stock.
     *
     * @param quantity_stock The quantity of the product in stock.
     */
    public void setQuantity_stock(int quantity_stock) {
        this.quantity_stock = quantity_stock;
    }

    /**
     * Gets the price of the product.
     *
     * @return The price of the product.
     */
    public float getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price The price of the product.
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Gets the unit of measurement for the product.
     *
     * @return The unit of measurement for the product.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit of measurement for the product.
     *
     * @param unit The unit of measurement for the product.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
