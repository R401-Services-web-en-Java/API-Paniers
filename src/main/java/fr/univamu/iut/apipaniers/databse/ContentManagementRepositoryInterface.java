package fr.univamu.iut.apipaniers.databse;

import fr.univamu.iut.apipaniers.content.Content;

import java.util.ArrayList;

public interface ContentManagementRepositoryInterface {
    public void close();
    public Content getContent(int basket_id, String product_name );

    public ArrayList<Content> getAllContents() ;

    public boolean updateContent(int basket_id, String product_name, int quantity);

    public void addContent(Content content);

    public void deleteContent(int basket_id,String product_name);
}
