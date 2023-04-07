package fr.univamu.iut.apipaniers.apis.user;

import fr.univamu.iut.apipaniers.beans.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class UserRepositoryApi implements UserRepositoryInterface{

    String url;


    /**
     * @param url
     */
    public UserRepositoryApi(String url){
        this.url = url ;
    }

    /**
     * Close the API connection
     */
    @Override
    public void close() {

    }

    /**
     * @param username
     * @return the User requested from the UserApi that has the username required
     */
    @Override
    public User getUser(String username) {
        User myUser = null;

        Client client = ClientBuilder.newClient();

        WebTarget bookResource = client.target(url);

        WebTarget bookEndpoint = bookResource.path("api/users/"+username);

        String token = "token";
        Invocation.Builder builder = bookEndpoint.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        Response response = builder.get();

        if (response.getStatus() == 200) {
            myUser = response.readEntity(User.class);
        }

        client.close();

        return myUser;
    }
}
