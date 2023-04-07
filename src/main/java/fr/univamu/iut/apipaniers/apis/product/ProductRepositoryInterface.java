package fr.univamu.iut.apipaniers.apis.product;

import fr.univamu.iut.apipaniers.beans.Product;

public interface ProductRepositoryInterface {
    public void close();

    public Product getProduct(String name) ;

    public boolean updateProduct(String name, Product newProduct);
}
