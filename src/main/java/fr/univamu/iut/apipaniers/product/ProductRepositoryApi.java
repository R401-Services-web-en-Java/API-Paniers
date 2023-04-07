package fr.univamu.iut.apipaniers.product;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ProductRepositoryApi implements ProductRepositoryInterface{
    String url;


    /**
     * @param url
     */
    public ProductRepositoryApi(String url){
        this.url = url ;
    }

    /**
     * Close the API connection
     */
    @Override
    public void close() {

    }

    /**
     * @param name
     * @return the Product requested from the ProductApi that has the name required
     */
    @Override
    public Product getProduct(String name) {
        Product myProduct = null;

        Client client = ClientBuilder.newClient();

        WebTarget bookResource = client.target(url);

        WebTarget bookEndpoint = bookResource.path("api/products/"+name);

        String token = "token";
        Invocation.Builder builder = bookEndpoint.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        Response response = builder.get();

        if (response.getStatus() == 200) {
            myProduct = response.readEntity(Product.class);
        }

        client.close();

        return myProduct;
    }

    /**
     * @param name
     * @param newProduct
     * @return if the product has been updated
     */
    @Override
    public boolean updateProduct(String name, Product newProduct) {
        Client client = ClientBuilder.newClient();

        WebTarget productResource = client.target(url);

        WebTarget productEndpoint = productResource.path("api/products/" + name);

        String token = "token";

        Invocation.Builder builder = productEndpoint.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        Response response = builder.put(Entity.entity(newProduct, MediaType.APPLICATION_JSON));

        client.close();

        return response.getStatus() == 200;
    }
}
