package fr.univamu.iut.apipaniers.databse;

import fr.univamu.iut.apipaniers.content.Content;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class ContentManagementRepositoryMariadb implements ContentManagementRepositoryInterface, Closeable {
    protected Connection dbConnection ;

    /**
     * @param infoConnection
     * @param user
     * @param pwd
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public ContentManagementRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
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
     * @param product_name
     * @return the Content equivalent to the id and product asked
     */
    @Override
    public Content getContent(int basket_id, String product_name) {
        Content selectedContent = null;

        String query = "SELECT * FROM CONTENT WHERE basket_id=? AND product_name=?";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1, basket_id);
            ps.setString(2,product_name);

            ResultSet result = ps.executeQuery();

            if( result.next() )
            {
                int quantity = result.getInt("quantity");

                selectedContent = new Content(basket_id, product_name,quantity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedContent;
    }

    /**
     * @return an ArrayList<Content> of all the contents
     */
    @Override
    public ArrayList<Content> getAllContents() {
        ArrayList<Content> listContents ;

        String query = "SELECT * FROM CONTENT";

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ResultSet result = ps.executeQuery();

            listContents = new ArrayList<>();

            while ( result.next() )
            {
                int basket_id = result.getInt("basket_id");
                String product_name = result.getString("product_name");
                int quantity = result.getInt("quantity");
                Content currentContent = new Content(basket_id, product_name,quantity);

                listContents.add(currentContent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listContents;
    }

    /**
     * @param content
     */
    @Override
    public void addContent(Content content) {
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

    /**
     * @param basket_id
     * @param product_name
     */
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

    /**
     * @param basket_id
     * @param product_name
     * @param quantity
     * @return if the update has been successful
     */
    @Override
    public boolean updateContent(int basket_id, String product_name, int quantity) {
        String query = "UPDATE CONTENT SET quantity=? where basket_id=? AND product_name=?";
        int nbRowModified = 0;

        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setInt(1,quantity);
            ps.setInt(2, basket_id);
            ps.setString(3, product_name );

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ( nbRowModified != 0 );
    }
}
