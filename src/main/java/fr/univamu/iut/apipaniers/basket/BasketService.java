package fr.univamu.iut.apipaniers.basket;

import fr.univamu.iut.apipaniers.BasketManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.user.UserRepositoryApi;
import fr.univamu.iut.apipaniers.user.UserRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class BasketService {
    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les livres
     */
    protected BasketManagementRepositoryInterface BasketRepo ;
    protected UserRepositoryInterface userRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param BasketRepo objet implémentant l'interface d'accès aux données
     */
    public  BasketService(BasketManagementRepositoryInterface BasketRepo, UserRepositoryInterface userRepo) {
        this.BasketRepo = BasketRepo;
        this.userRepo = userRepo;
    }

    /**
     * Méthode retournant les informations sur les livres au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllBasketsJSON(){

        ArrayList<Basket> allBaskets = BasketRepo.getAllBaskets();

        // création du json et conversion de la liste de livres
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allBaskets);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un livre recherché
     * @param basket_id la référence du livre recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getBasketJSON( int basket_id, String username){
        String result = null;
        Basket myBasket = BasketRepo.getBasket(basket_id,username);

        // si le livre a été trouvé
        if( myBasket != null ) {

            // création du json et conversion du livre
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myBasket);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }


    public boolean updateBasket(int basket_id,String username, Basket Basket) {
        return BasketRepo.updateBasket(basket_id, username, Basket.confirmation_date, Basket.confirmed);
    }

    public void addBasket(Basket basket) {
        if (BasketRepo.getBasket(basket.getBasket_id(),basket.getUsername()) != null) {
            throw new RuntimeException("Basket already exists");
        }
        BasketRepo.addBasket(basket);
    }

    public void deleteBasket(int basket_id, String username) {
        if (BasketRepo.getBasket(basket_id,username) == null) {
            throw new NotFoundException();
        }
        BasketRepo.deleteBasket(basket_id,username);
    }

    public String getBasketsByUsernameJSON(String username) {
        if (userRepo.getUser(username) == null) {
            throw new NotFoundException();
        }
        ArrayList<Basket> allBaskets = BasketRepo.getBasketsByUsername(username);

        // création du json et conversion de la liste de livres
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allBaskets);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }
}
