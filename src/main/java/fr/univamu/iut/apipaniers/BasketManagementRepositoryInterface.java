package fr.univamu.iut.apipaniers;

import fr.univamu.iut.apipaniers.basket.Basket;
import fr.univamu.iut.apipaniers.content.Content;

import java.sql.Date;
import java.util.ArrayList;

public interface BasketManagementRepositoryInterface {
    //Basket
    public void close();

    public Basket getBasket(int basket_id , String username);

    public ArrayList<Basket> getAllBaskets() ;

    public boolean updateBasket(int basket_id, String username, Date confirmation_date, boolean confirmed);

    public void addBasket(Basket basket);

    public void deleteBasket(int basket_id, String username);

    public ArrayList<Basket> getBasketsByUsername(String username);

    //Content
    public Content getContent(int basket_id, String product_name );

    public ArrayList<Content> getAllContents() ;

    public boolean updateContent(int basket_id, String product_name, int quantity);

    public void addContent(Content content);

    public void deleteContent(int basket_id,String product_name);
}
