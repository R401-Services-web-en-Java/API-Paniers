package fr.univamu.iut.apipaniers.beans;

import java.sql.Date;

/**
 * Basket Class from the DB
 */
public class Basket {
    private int basket_id;
    private Date confirmation_date;
    private boolean confirmed;
    private String username;

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
