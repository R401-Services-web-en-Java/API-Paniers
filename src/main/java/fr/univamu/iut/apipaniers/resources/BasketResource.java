package fr.univamu.iut.apipaniers.resources;

import fr.univamu.iut.apipaniers.services.BasketService;
import fr.univamu.iut.apipaniers.beans.Basket;
import fr.univamu.iut.apipaniers.databse.BasketManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.apis.user.UserRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

/**
 * Resource api class
 * @acces url/api/baskets
 */
@Path("/baskets")
@ApplicationScoped
public class BasketResource {

    private BasketService service;

    /**
     * Default constructor
     */
    public BasketResource(){}


    /**
     * Constructor to init the service
     *
     * @param BasketRepo
     * @param userRepo
     */
    public @Inject BasketResource(BasketManagementRepositoryInterface BasketRepo, UserRepositoryInterface userRepo ){
        this.service = new BasketService( BasketRepo,userRepo) ;
    }

    /**
     * @param service
     */
    public BasketResource( BasketService service ){
        this.service = service;
    }

    /**
     * Get all the baskets from the BD
     *
     * @return all the baskets in JSON format
     */
    @GET
    @Produces("application/json")
    public String getAllBaskets() {
        return service.getAllBasketsJSON();
    }


    /**
     * Read method from the CRUD of Basket
     *
     * @param basket_id
     * @return
     */
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

    /**
     * Create method from the CRUD of Basket
     *
     * @param basketJson
     * @return api response
     */
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

    /**
     * Delete method from the CRUD of Basket
     *
     * @param basket_id
     * @return api response
     */
    @DELETE
    @Path("{basket_id}")
    public Response deleteBasket(@PathParam("basket_id") int basket_id){
        service.deleteBasket(basket_id);
        return Response.noContent().build();
    }

    /**
     * Update method from the CRUD of Basket
     *
     * @param basket_id
     * @param basketJson
     * @return api response
     */
    @PUT
    @Path("{basket_id}")
    @Consumes("application/json")
    public Response updateBasket(@PathParam("basket_id") int basket_id, String basketJson ) {
        try {
            Basket basket = new ObjectMapper().readValue(basketJson, Basket.class);

            service.updateBasket(basket_id, basket);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * Get all the baskets of a user
     *
     * @param username
     * @return JSON string with the result
     */
    @GET
    @Path("{username}")
    @Consumes("application/json")
    public String getBasketsByUsername(@PathParam("username") String username ){
        return service.getBasketsByUsernameJSON(username);
    }
}
