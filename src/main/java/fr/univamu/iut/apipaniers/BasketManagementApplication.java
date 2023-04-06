package fr.univamu.iut.apipaniers;

import fr.univamu.iut.apipaniers.basket.BasketResource;
import fr.univamu.iut.apipaniers.basket.BasketService;
import fr.univamu.iut.apipaniers.content.ContentResource;
import fr.univamu.iut.apipaniers.content.ContentService;
import fr.univamu.iut.apipaniers.user.UserRepositoryApi;
import fr.univamu.iut.apipaniers.user.UserRepositoryInterface;
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

    @Produces
    private BasketManagementRepositoryInterface openDbConnection(){
        BasketManagementRepositoryMariadb db = null;

        try{
            db = new BasketManagementRepositoryMariadb("jdbc:mariadb://mysql-lucaceccarelli.alwaysdata.net/lucaceccarelli_basket", "300238_api", "MotDePasse13");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes BasketManagementRepositoryInterface basketRepo ) {
        basketRepo.close();
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> set = new HashSet<>();

        BasketService basketService = null ;
        ContentService contentService = null;
        try {
            BasketManagementRepositoryMariadb db = new BasketManagementRepositoryMariadb("jdbc:mariadb://mysql-lucaceccarelli.alwaysdata.net/lucaceccarelli_basket", "300238_api", "MotDePasse13");
            basketService = new BasketService(db,connectUserApi());
            contentService = new ContentService(db);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // Création de la ressource en lui passant paramètre les services à exécuter en fonction
        // des différents endpoints proposés (i.e. requêtes HTTP acceptées)
        set.add(new BasketResource(basketService));
        set.add(new ContentResource(contentService));

        return set;
    }

    @Produces
    private UserRepositoryInterface connectUserApi(){
        return new UserRepositoryApi("http://localhost:8080/API-Produits-et-Utilisateurs-1.0-SNAPSHOT/");
    }
}

