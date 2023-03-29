package fr.univamu.iut.apipaniers.content;

import fr.univamu.iut.apipaniers.BasketManagementRepositoryInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

public class ContentResource {

    private ContentService service;

    public ContentResource(){}


    public @Inject ContentResource(BasketManagementRepositoryInterface contentRepo ){
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

        // si le livre n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    @PUT
    @Path("{content_id}/{product_name}")
    @Consumes("application/json")
    public Response updateContent(@PathParam("content_id") int content_id,@PathParam("product_name") String product_name, Content Content ){

        // si le livre n'a pas été trouvé
        if( ! service.updateContent(content_id, product_name, Content) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }
}
