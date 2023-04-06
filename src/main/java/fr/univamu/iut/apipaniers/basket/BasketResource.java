package fr.univamu.iut.apipaniers.basket;

import fr.univamu.iut.apipaniers.databse.BasketManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.user.UserRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

@Path("/baskets")
@ApplicationScoped
public class BasketResource {

    /**
     * Service utilisé pour accéder aux données des livres et récupérer/modifier leurs informations
     */
    private BasketService service;

    /**
     * Constructeur par défaut
     */
    public BasketResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param BasketRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject BasketResource(BasketManagementRepositoryInterface BasketRepo, UserRepositoryInterface userRepo ){
        this.service = new BasketService( BasketRepo,userRepo) ;
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux livres
     */
    public BasketResource( BasketService service ){
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de tous les livres enregistrés
     * @return la liste des livres (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllBaskets() {
        return service.getAllBasketsJSON();
    }


    @GET
    @Path("{basket_id}/{username}")
    @Produces("application/json")
    public String getBasket( @PathParam("basket_id") int basket_id, @PathParam("username") String username){

        String result = service.getBasketJSON(basket_id,username);

        // si le livre n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    @POST
    @Consumes("application/json")
    public Response addBasket(String basketJson){
        try {
            Basket basket = new ObjectMapper().readValue(basketJson, Basket.class);

            service.addBasket(basket);

            URI location = new URI("/baskets/" + basket.getBasket_id());
            return Response.created(location).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @DELETE
    @Path("{basket_id}/{username}")
    public Response deleteBasket(@PathParam("basket_id") int basket_id, @PathParam("username") String username){
        service.deleteBasket(basket_id,username);
        return Response.noContent().build();
    }
    @PUT
    @Path("{basket_id}/{username}")
    @Consumes("application/json")
    public Response updateBasket(@PathParam("basket_id") int basket_id, @PathParam("username") String username, String basketJson ) {
        try {
            Basket basket = new ObjectMapper().readValue(basketJson, Basket.class);

            service.updateBasket(basket_id, username, basket);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{username}")
    @Consumes("application/json")
    public String getBasketsByUsername(@PathParam("username") String username ){
        return service.getBasketsByUsernameJSON(username);
    }
}
