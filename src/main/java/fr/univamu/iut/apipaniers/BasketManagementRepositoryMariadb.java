package fr.univamu.iut.apipaniers;

import fr.univamu.iut.apipaniers.basket.Basket;
import fr.univamu.iut.apipaniers.content.Content;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class BasketManagementRepositoryMariadb implements BasketManagementRepositoryInterface, Closeable {
    protected Connection dbConnection ;

    public BasketManagementRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public Basket getBasket(int basket_id, String username) {

        Basket selectedBasket = null;

        String query = "SELECT * FROM BASKET WHERE basket_id=? AND username=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, basket_id);
            ps.setString(2, username);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            // récupération du premier (et seul) tuple résultat
            // (si la référence du livre est valide)
            if( result.next() )
            {
                Date confirmation_date = result.getDate("confirmation_date");
                Boolean confirmed = result.getBoolean("confirmed");

                // création du livre courant
                Basket currentBasket = new Basket(basket_id, confirmation_date, confirmed,username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedBasket;
    }

    @Override
    public ArrayList<Basket> getAllBaskets() {
        ArrayList<Basket> listBaskets ;

        String query = "SELECT * FROM BASKET";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listBaskets = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                int basket_id = result.getInt("basket_id");
                Date confirmation_date = result.getDate("confirmation_date");
                Boolean confirmed = result.getBoolean("confirmed");
                String username = result.getString("username");

                // création du livre courant
                Basket currentBasket = new Basket(basket_id, confirmation_date, confirmed,username);

                listBaskets.add(currentBasket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBaskets;
    }

    @Override
    public boolean updateBasket(int basket_id, String username, Date confirmation_date, boolean confirmed) {
        String query = "UPDATE BASKET SET confirmation_date=?, confirmed=? WHERE basket_id=? AND username=?";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setDate(1, confirmation_date);
            ps.setBoolean(2, confirmed);
            ps.setInt(3, basket_id);
            ps.setString(4, username );

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }

    @Override
    public Content getContent(int basket_id, String product_name) {
        Content selectedContent = null;

        String query = "SELECT * FROM CONTENT WHERE basket_id=? AND product_name=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, basket_id);
            ps.setString(2,product_name);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            // récupération du premier (et seul) tuple résultat
            // (si la référence du livre est valide)
            if( result.next() )
            {
                int quantity = result.getInt("quantity");

                // création du livre courant
                Content currentContent = new Content(basket_id, product_name,quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedContent;
    }

    @Override
    public ArrayList<Content> getAllContents() {
        ArrayList<Content> listContents ;

        String query = "SELECT * FROM CONTENT";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listContents = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                int basket_id = result.getInt("basket_id");
                String product_name = result.getString("product_name");
                int quantity = result.getInt("quantity");
                // création du livre courant
                Content currentContent = new Content(basket_id, product_name,quantity);

                listContents.add(currentContent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listContents;
    }

    @Override
    public boolean updateContent(int basket_id, String product_name, int quantity) {
        String query = "UPDATE CONTENT SET quantity=? where basket_id=? AND product_name=?";
        int nbRowModified = 0;

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1,quantity);
            ps.setInt(2, basket_id);
            ps.setString(3, product_name );

            // exécution de la requête
            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }


    @Override
    public void addBasket(Basket basket) {
        String query = "INSERT INTO BASKET (basket_id, confirmation_date, confirmed, username) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, basket.getBasket_id());
            ps.setDate(2, basket.getConfirmation_date());
            ps.setBoolean(3, basket.isConfirmed());
            ps.setString(4, basket.getUsername());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBasket(int basket_id, String username) {
        String query = "DELETE FROM BASKET WHERE basket_id = ? AND username=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, basket_id);
            ps.setString(2,username);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addContent(Content content) {
        //    public Content(int basket_id, String product_name, int quantity) {
        String query = "INSERT INTO CONTENT (basket_id, product_name, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, content.getBasket_id());
            ps.setString(2, content.getProduct_name());
            ps.setInt(3, content.getQuantity());


            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteContent(int basket_id,String product_name) {
        String query = "DELETE FROM BASKET WHERE basket_id = ? AND product_name = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setInt(1, basket_id);
            ps.setString(2,product_name);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
