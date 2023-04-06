package fr.univamu.iut.apipaniers.basket;

import java.sql.Date;

/**
 * Basket Class from the DB
 */
public class Basket {
    protected int basket_id;
    protected Date confirmation_date;
    protected boolean confirmed;
    protected String username;

    public Basket() {
    }

    /**
     * @param basket_id
     * @param confirmation_date
     * @param confirmed
     * @param username
     */
    public Basket(int basket_id, Date confirmation_date, boolean confirmed, String username) {
        this.basket_id = basket_id;
        this.confirmation_date = confirmation_date;
        this.confirmed = confirmed;
        this.username = username;
    }

    public int getBasket_id() {
        return basket_id;
    }

    public void setBasket_id(int basket_id) {
        this.basket_id = basket_id;
    }

    public Date getConfirmation_date() {
        return confirmation_date;
    }

    public void setConfirmation_date(Date confirmation_date) {
        this.confirmation_date = confirmation_date;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
