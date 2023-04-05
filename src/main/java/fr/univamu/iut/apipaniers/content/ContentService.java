package fr.univamu.iut.apipaniers.content;

import fr.univamu.iut.apipaniers.BasketManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.basket.Basket;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class ContentService {

    protected BasketManagementRepositoryInterface ContentRepo ;

    public  ContentService( BasketManagementRepositoryInterface ContentRepo) {
        this.ContentRepo = ContentRepo;
    }

    public String getAllContentsJSON(){

        ArrayList<Content> allContents = ContentRepo.getAllContents();

        // création du json et conversion de la liste de livres
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allContents);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    public String getContentJSON( int content_id, String product_name ){
        String result = null;
        Content myContent = ContentRepo.getContent(content_id,product_name);

        // si le livre a été trouvé
        if( myContent != null ) {

            // création du json et conversion du livre
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myContent);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }


    public boolean updateContent(int content_id,String product_name, Content Content) {
        return ContentRepo.updateContent(content_id, product_name, Content.quantity);
    }

    public void addContent(Content content) {
        if (ContentRepo.getContent(content.getBasket_id(),content.getProduct_name()) != null) {
            throw new RuntimeException("Content already exists");
        }
        ContentRepo.addContent(content);
    }

    public void deleteContent(int basket_id,String product_name) {
        if (ContentRepo.getContent(basket_id,product_name) == null) {
            throw new NotFoundException();
        }
        ContentRepo.deleteContent(basket_id,product_name);
    }
}
