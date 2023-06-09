package fr.univamu.iut.apipaniers.services;

import fr.univamu.iut.apipaniers.beans.Basket;
import fr.univamu.iut.apipaniers.databse.BasketManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.apis.user.UserRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

/**
 * Basket service class
 */
public class BasketService {
    protected BasketManagementRepositoryInterface basketRepo ;
    protected UserRepositoryInterface userRepo;

    /**
     * Constructor that initialize the repositories
     *
     * @param basketRepo
     * @param userRepo
     */
    public  BasketService(BasketManagementRepositoryInterface basketRepo, UserRepositoryInterface userRepo) {
        this.basketRepo = basketRepo;
        this.userRepo = userRepo;
    }

    /**
     * @return all the baskets in JSON format
     */
    public String getAllBasketsJSON(){

        ArrayList<Basket> allBaskets = basketRepo.getAllBaskets();

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
     * @param basket_id
     * @return a Basket in JSON format
     */
    public String getBasketJSON( int basket_id){
        if (basketRepo.getBasket(basket_id) == null) {
            throw new NotFoundException();
        }
        String result = null;
        Basket myBasket = basketRepo.getBasket(basket_id);

        if( myBasket != null ) {

            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myBasket);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }


    /**
     * @param basket_id
     * @param basket
     * @return if the basket as been updated
     */
    public boolean updateBasket(int basket_id, Basket basket) {
        if (basketRepo.getBasket(basket_id) == null) {
            throw new NotFoundException();
        }
        return basketRepo.updateBasket(basket_id, basket.getUsername(), basket.getConfirmation_date(), basket.isConfirmed());
    }

    /**
     * @param basket
     */
    public void addBasket(Basket basket) {
        if (basketRepo.getBasket(basket.getBasket_id()) != null) {
            throw new RuntimeException("Basket already exists");
        }
        basketRepo.addBasket(basket);
    }

    /**
     * @param basket_id
     */
    public void deleteBasket(int basket_id) {
        if (basketRepo.getBasket(basket_id) == null) {
            throw new NotFoundException();
        }
        basketRepo.deleteBasket(basket_id);
    }

    /**
     * @param username
     * @return the list of the baskets of a user in a JSON format
     */
    public String getBasketsByUsernameJSON(String username) {
        if (userRepo.getUser(username) == null) {
            throw new NotFoundException();
        }
        ArrayList<Basket> allBaskets = basketRepo.getBasketsByUsername(username);

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
