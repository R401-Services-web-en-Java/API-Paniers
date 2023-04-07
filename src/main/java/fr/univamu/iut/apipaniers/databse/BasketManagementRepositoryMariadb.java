package fr.univamu.iut.apipaniers.databse;

import fr.univamu.iut.apipaniers.beans.Basket;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class BasketManagementRepositoryMariadb implements BasketManagementRepositoryInterface, Closeable {
    protected Connection dbConnection ;

    /**
     * @param infoConnection
     * @param user
     * @param pwd
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public BasketManagementRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    /**
     * Close the connection
     */
    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * @param basket_id
     * @param username
     * @return the basket equivalent to the id and username
     */
    @Override
    public Basket getBasket(int basket_id, String username) {

        Basket selectedBasket = null;

        String query = "SELECT * FROM BASKET WHERE basket_id=? AND username=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, basket_id);
            ps.setString(2, username);

            ResultSet result = ps.executeQuery();

            if( result.next() )
            {
                Date confirmation_date = result.getDate("confirmation_date");
                Boolean confirmed = result.getBoolean("confirmed");

                selectedBasket = new Basket(basket_id, confirmation_date, confirmed,username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedBasket;
    }

    /**
     * @return an ArrayList<Basket> of all the baskets
     */
    @Override
    public ArrayList<Basket> getAllBaskets() {
        ArrayList<Basket> listBaskets ;

        String query = "SELECT * FROM BASKET";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ResultSet result = ps.executeQuery();

            listBaskets = new ArrayList<>();

            while ( result.next() )
            {
                int basket_id = result.getInt("basket_id");
                Date confirmation_date = result.getDate("confirmation_date");
                Boolean confirmed = result.getBoolean("confirmed");
                String username = result.getString("username");

                Basket currentBasket = new Basket(basket_id, confirmation_date, confirmed,username);

                listBaskets.add(currentBasket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBaskets;
    }

    /**
     * @param basket_id
     * @param username
     * @param confirmation_date
     * @param confirmed
     * @return if the basket has been updated
     */
    @Override
    public boolean updateBasket(int basket_id, String username, Date confirmation_date, boolean confirmed) {
        String query = "UPDATE BASKET SET confirmation_date=?, confirmed=? WHERE basket_id=? AND username=?";
        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setDate(1, confirmation_date);
            ps.setBoolean(2, confirmed);
            ps.setInt(3, basket_id);
            ps.setString(4, username );

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }

    /**
     * @param basket
     */
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

    /**
     * @param basket_id
     * @param username
     */
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

    /**
     * @param username
     * @return an ArrayList<Basket> of all the baskets of a user
     */
    @Override
    public ArrayList<Basket> getBasketsByUsername(String username) {
        ArrayList<Basket> listBaskets;

        String query = "SELECT * FROM BASKET WHERE username=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1,username);

            ResultSet result = ps.executeQuery();

            listBaskets = new ArrayList<>();

            while ( result.next() )
            {
                int basket_id = result.getInt("basket_id");
                Date confirmation_date = result.getDate("confirmation_date");
                Boolean confirmed = result.getBoolean("confirmed");
                String basketUsername = result.getString("username");

                Basket currentBasket = new Basket(basket_id, confirmation_date, confirmed,basketUsername);

                listBaskets.add(currentBasket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listBaskets;
    }
}
