package fr.univamu.iut.apipaniers.databse;

import fr.univamu.iut.apipaniers.basket.Basket;
import fr.univamu.iut.apipaniers.content.Content;

import java.sql.Date;
import java.util.ArrayList;

public interface BasketManagementRepositoryInterface {
    public void close();

    public Basket getBasket(int basket_id , String username);

    public ArrayList<Basket> getAllBaskets() ;

    public boolean updateBasket(int basket_id, String username, Date confirmation_date, boolean confirmed);

    public void addBasket(Basket basket);

    public void deleteBasket(int basket_id, String username);

    public ArrayList<Basket> getBasketsByUsername(String username);
}
