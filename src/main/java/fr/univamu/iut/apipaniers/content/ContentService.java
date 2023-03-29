package fr.univamu.iut.apipaniers.content;

import fr.univamu.iut.apipaniers.BasketManagementRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

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
}
