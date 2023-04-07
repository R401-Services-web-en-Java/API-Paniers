package fr.univamu.iut.apipaniers.beans;


/**
 * Content DB equivalent
 */
public class Content {
    private int basket_id;
    private String product_name;
    private int quantity;

    /**
     * Default constructor
     */
    public Content() {
    }

    /**
     * @param basket_id
     * @param product_name
     * @param quantity
     */
    public Content(int basket_id, String product_name, int quantity) {
        this.basket_id = basket_id;
        this.product_name = product_name;
        this.quantity = quantity;
    }

    public int getBasket_id() {
        return basket_id;
    }

    public void setBasket_id(int basket_id) {
        this.basket_id = basket_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
