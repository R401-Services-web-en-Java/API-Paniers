package fr.univamu.iut.apipaniers.content;

public class Content {
    protected int basket_id;
    protected String product_name;
    protected int quantity;

    public Content() {
    }

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
