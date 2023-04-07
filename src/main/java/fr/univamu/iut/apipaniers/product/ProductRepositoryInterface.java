package fr.univamu.iut.apipaniers.product;

import fr.univamu.iut.apipaniers.user.User;

public interface ProductRepositoryInterface {
    public void close();

    public Product getProduct(String name) ;

    public boolean updateProduct(String name, Product newProduct);
}
