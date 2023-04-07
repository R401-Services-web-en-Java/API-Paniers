package fr.univamu.iut.apipaniers.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univamu.iut.apipaniers.beans.Content;
import fr.univamu.iut.apipaniers.services.ContentService;
import fr.univamu.iut.apipaniers.databse.ContentManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.apis.product.ProductRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;

/**
 * Content Resource class
 * @access url/api/contents
 */
@Path("/contents")
@ApplicationScoped
public class ContentResource {

    private ContentService service;

    /**
     * Default constructor
     */
    public ContentResource(){}


    /**
     * @param contentRepo
     */
    public @Inject ContentResource(ContentManagementRepositoryInterface contentRepo , ProductRepositoryInterface productRepo){
        this.service = new ContentService( contentRepo,productRepo) ;
    }


    /**
     * @param service
     */
    public ContentResource( ContentService service ){
        this.service = service;
    }


    /**
     * @return all the contents in JSON
     */
    @GET
    @Produces("application/json")
    public String getAllContents() {
        return service.getAllContentsJSON();
    }


    /**
     * @param content_id
     * @param product_name
     * @return a content in JSON format
     */
    @GET
    @Path("{content_id}/{product_name}")
    @Produces("application/json")
    public String getContent( @PathParam("content_id") int content_id,@PathParam("product_name") String product_name){

        String result = service.getContentJSON(content_id,product_name);

        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * @param contentJson
     * @return if the creation is successful
     */
    @POST
    @Consumes("application/json")
    public Response addContent(String contentJson){
        try {
            Content content = new ObjectMapper().readValue(contentJson, Content.class);

            service.addContent(content);

            URI location = new URI("/contents/" + content.getBasket_id() + content.getProduct_name());
            return Response.created(location).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    /**
     * @param content_id
     * @param product_name
     * @return if the deletion is successful
     */
    @DELETE
    @Path("{content_id}/{product_name}")
    public Response deleteContent(@PathParam("content_id") int content_id,@PathParam("product_name") String product_name){
        service.deleteContent(content_id,product_name);
        return Response.noContent().build();
    }

    /**
     * @param content_id
     * @param product_name
     * @param contentJson
     * @return if the update is successful
     */
    @PUT
    @Path("{content_id}/{product_name}")
    @Consumes("application/json")
    public Response updateContent(@PathParam("content_id") int content_id,@PathParam("product_name") String product_name, String contentJson ){
        try {
            Content content = new ObjectMapper().readValue(contentJson, Content.class);

            service.updateContent(content_id,product_name,content);

            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
