package fr.univamu.iut.apipaniers.databse;

import fr.univamu.iut.apipaniers.basket.Basket;

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
    public ArrayList<Basket> getBasketsByUsername(String username) {
        ArrayList<Basket> listBaskets;

        String query = "SELECT * FROM BASKET WHERE username=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            //Prepare the username given
            ps.setString(1,username);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            listBaskets = new ArrayList<>();

            // récupération du premier (et seul) tuple résultat
            while ( result.next() )
            {
                int basket_id = result.getInt("basket_id");
                Date confirmation_date = result.getDate("confirmation_date");
                Boolean confirmed = result.getBoolean("confirmed");
                String basketUsername = result.getString("username");

                // création du livre courant
                Basket currentBasket = new Basket(basket_id, confirmation_date, confirmed,basketUsername);

                listBaskets.add(currentBasket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBaskets;
    }
}
