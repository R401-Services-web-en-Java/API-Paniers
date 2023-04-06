package fr.univamu.iut.apipaniers.content;

import fr.univamu.iut.apipaniers.databse.ContentManagementRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class ContentService {

    protected ContentManagementRepositoryInterface contentRepo ;

    public  ContentService( ContentManagementRepositoryInterface contentRepo) {
        this.contentRepo = contentRepo;
    }

    public String getAllContentsJSON(){

        ArrayList<Content> allContents = contentRepo.getAllContents();

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
        Content myContent = contentRepo.getContent(content_id,product_name);

        if( myContent != null ) {

            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myContent);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }


    public boolean updateContent(int content_id,String product_name, Content Content) {
        return contentRepo.updateContent(content_id, product_name, Content.quantity);
    }

    public void addContent(Content content) {
        if (contentRepo.getContent(content.getBasket_id(),content.getProduct_name()) != null) {
            throw new RuntimeException("Content already exists");
        }
        contentRepo.addContent(content);
    }

    public void deleteContent(int basket_id,String product_name) {
        if (contentRepo.getContent(basket_id,product_name) == null) {
            throw new NotFoundException();
        }
        contentRepo.deleteContent(basket_id,product_name);
    }
}
