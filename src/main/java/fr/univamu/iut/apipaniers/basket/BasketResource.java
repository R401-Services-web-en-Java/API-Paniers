package fr.univamu.iut.apipaniers.basket;

import fr.univamu.iut.apipaniers.BasketManagementRepositoryInterface;
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
    public @Inject BasketResource(BasketManagementRepositoryInterface BasketRepo ){
        this.service = new BasketService( BasketRepo) ;
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
    @Path("{basket_id}")
    @Produces("application/json")
    public String getBasket( @PathParam("basket_id") int basket_id){

        String result = service.getBasketJSON(basket_id);

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
    @Path("{basket_id}")
    public Response deleteBasket(@PathParam("basket_id") int basket_id){
        service.deleteBasket(basket_id);
        return Response.noContent().build();
    }
    @PUT
    @Path("{basket_id}")
    @Consumes("application/json")
    public Response updateBasket(@PathParam("basket_id") int basket_id, String basketJson ){
        try {
            Basket basket = new ObjectMapper().readValue(basketJson, Basket.class);

            service.updateBasket(basket_id,basket);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


}
