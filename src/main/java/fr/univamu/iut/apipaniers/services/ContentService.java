package fr.univamu.iut.apipaniers.services;

import fr.univamu.iut.apipaniers.beans.Content;
import fr.univamu.iut.apipaniers.databse.ContentManagementRepositoryInterface;
import fr.univamu.iut.apipaniers.beans.Product;
import fr.univamu.iut.apipaniers.apis.product.ProductRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

/**
 * Content service class
 */
public class ContentService {

    protected ContentManagementRepositoryInterface contentRepo ;
    protected ProductRepositoryInterface productRepo;

    /**
     * @param contentRepo
     * @param productRepo
     */
    public  ContentService( ContentManagementRepositoryInterface contentRepo, ProductRepositoryInterface productRepo) {
        this.contentRepo = contentRepo;
        this.productRepo = productRepo;
    }

    /**
     * @return all the contents in JSON format
     */
    public String getAllContentsJSON(){

        ArrayList<Content> allContents = contentRepo.getAllContents();

        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allContents);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * @param content_id
     * @param product_name
     * @return a content in JSON format
     */
    public String getContentJSON( int content_id, String product_name ){
        String result = null;
        Content myContent = contentRepo.getContent(content_id,product_name);

        if( myContent != null ) {

            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myContent);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }


    /**
     * @param content_id
     * @param product_name
     * @param content
     * @return if the content has been updated
     */
    public boolean updateContent(int content_id,String product_name, Content content) {
        if(productRepo.getProduct(content.getProduct_name()) ==  null){
            throw new RuntimeException("Product doesnt exists");
        }
        Product newProduct = productRepo.getProduct(content.getProduct_name());
        Content oldContent = contentRepo.getContent(content_id,product_name);

        //Verify if it's needed to diminish the quantity in stock or augment it
        if(content.getQuantity()>oldContent.getQuantity()){
            newProduct.setQuantity_stock(newProduct.getQuantity_stock() - (content.getQuantity()-oldContent.getQuantity()));
        }else{
            newProduct.setQuantity_stock(newProduct.getQuantity_stock() + (oldContent.getQuantity()-content.getQuantity()));
        }
        productRepo.updateProduct(product_name,newProduct);
        return contentRepo.updateContent(content_id, product_name, content.getQuantity());
    }

    /**
     * @param content
     */
    public void addContent(Content content) {
        if (contentRepo.getContent(content.getBasket_id(),content.getProduct_name()) != null) {
            throw new RuntimeException("Content already exists");
        }
        if(productRepo.getProduct(content.getProduct_name()) ==  null){
            throw new NotFoundException("Product doesnt exists");
        }
        contentRepo.addContent(content);
    }

    /**
     * @param basket_id
     * @param product_name
     */
    public void deleteContent(int basket_id,String product_name) {
        if (contentRepo.getContent(basket_id,product_name) == null) {
            throw new NotFoundException();
        }
        contentRepo.deleteContent(basket_id,product_name);
    }
}
