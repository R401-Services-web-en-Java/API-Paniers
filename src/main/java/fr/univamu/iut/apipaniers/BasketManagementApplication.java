package fr.univamu.iut.apipaniers;

import fr.univamu.iut.apipaniers.resources.BasketResource;
import fr.univamu.iut.apipaniers.services.BasketService;
import fr.univamu.iut.apipaniers.resources.ContentResource;
import fr.univamu.iut.apipaniers.services.ContentService;
import fr.univamu.iut.apipaniers.databse.BasketManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.databse.BasketManagementRepositoryMariadb;
import fr.univamu.iut.apipaniers.databse.ContentManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.databse.ContentManagementRepositoryMariadb;
import fr.univamu.iut.apipaniers.apis.product.ProductRepositoryApi;
import fr.univamu.iut.apipaniers.apis.product.ProductRepositoryInterface;
import fr.univamu.iut.apipaniers.apis.user.UserRepositoryApi;
import fr.univamu.iut.apipaniers.apis.user.UserRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
@ApplicationScoped
public class BasketManagementApplication extends Application {

    /**
     * @return BasketManagementRepositoryInterface instance after opening the connection to the DB
     */
    @Produces
    private BasketManagementRepositoryInterface openBasketDbConnection(){
        BasketManagementRepositoryMariadb db = null;

        try{
            db = new BasketManagementRepositoryMariadb("jdbc:mariadb://mysql-lucaceccarelli.alwaysdata.net/lucaceccarelli_basket", "300238_api", "MotDePasse13");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * @return ContentManagementRepositoryInterface instance after opening the connection to the DB
     */
    @Produces
    private ContentManagementRepositoryInterface openContentDbConnection(){
        ContentManagementRepositoryMariadb db = null;

        try{
            db = new ContentManagementRepositoryMariadb("jdbc:mariadb://mysql-lucaceccarelli.alwaysdata.net/lucaceccarelli_basket", "300238_api", "MotDePasse13");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * @param basketRepo
     */
    private void closeBasketDbConnection(@Disposes BasketManagementRepositoryInterface basketRepo ) {
        basketRepo.close();
    }

    /**
     * @param contentRepo
     */
    private void closeContentDbConnection(@Disposes ContentManagementRepositoryInterface contentRepo ) {
        contentRepo.close();
    }

    /**
     * @return Set of all the objects needed to start the API
     */
    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        BasketService basketService = null ;
        ContentService contentService = null;
        try {
            BasketManagementRepositoryMariadb dbBasket = new BasketManagementRepositoryMariadb("jdbc:mariadb://mysql-lucaceccarelli.alwaysdata.net/lucaceccarelli_basket", "300238_api", "MotDePasse13");
            ContentManagementRepositoryMariadb dbContent = new ContentManagementRepositoryMariadb("jdbc:mariadb://mysql-lucaceccarelli.alwaysdata.net/lucaceccarelli_basket", "300238_api", "MotDePasse13");
            basketService = new BasketService(dbBasket,connectUserApi());
            contentService = new ContentService(dbContent,connectProductApi());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        set.add(new AuthenticationFilter());
        set.add(new BasketResource(basketService));
        set.add(new ContentResource(contentService));

        return set;
    }

    /**
     * @return The UserRepositoryApi instance of the User API launcher in background
     */
    @Produces
    private UserRepositoryInterface connectUserApi(){
        return new UserRepositoryApi("http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/");
    }


    /**
     * @return The ProductRepositoryApi instance of the Product API launcher in background
     */
    @Produces
    private ProductRepositoryInterface connectProductApi(){
        return new ProductRepositoryApi("http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/");
    }
    //TOOD: getAllContentsFromBasket , modif clef primaire username -> faire tous les contenus dans un basket
}

