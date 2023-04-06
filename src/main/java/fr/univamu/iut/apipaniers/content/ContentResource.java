package fr.univamu.iut.apipaniers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univamu.iut.apipaniers.databse.ContentManagementRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;

@Path("/contents")
@ApplicationScoped
public class ContentResource {

    private ContentService service;

    public ContentResource(){}


    public @Inject ContentResource(ContentManagementRepositoryInterface contentRepo ){
        this.service = new ContentService( contentRepo) ;
    }


    public ContentResource( ContentService service ){
        this.service = service;
    }


    @GET
    @Produces("application/json")
    public String getAllContents() {
        return service.getAllContentsJSON();
    }


    @GET
    @Path("{content_id}/{product_name}")
    @Produces("application/json")
    public String getContent( @PathParam("content_id") int content_id,@PathParam("product_name") String product_name){

        String result = service.getContentJSON(content_id,product_name);

        if( result == null )
            throw new NotFoundException();

        return result;
    }

    @POST
    @Consumes("application/json")
    public Response addContent(String contentJson){
        try {
            Content content = new ObjectMapper().readValue(contentJson, Content.class);

            service.addContent(content);

            URI location = new URI("/contents/" + content.getBasket_id() + content.product_name);
            return Response.created(location).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    @DELETE
    @Path("{content_id}/{product_name}")
    public Response deleteContent(@PathParam("content_id") int content_id,@PathParam("product_name") String product_name){
        service.deleteContent(content_id,product_name);
        return Response.noContent().build();
    }

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
