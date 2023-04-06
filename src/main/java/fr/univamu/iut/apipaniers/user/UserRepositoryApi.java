package fr.univamu.iut.apipaniers.user;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class UserRepositoryApi implements UserRepositoryInterface{

    /**
     * URL de l'API des utilisateurs
     */
    String url;

    /**
     * Constructeur initialisant l'url de l'API
     * @param url chaîne de caractères avec l'url de l'API
     */
    public UserRepositoryApi(String url){
        this.url = url ;
    }

    @Override
    public void close() {

    }

    @Override
    public User getUser(String username) {
        User myUser = null;

        // création d'un client
        Client client = ClientBuilder.newClient();

        // définition de l'adresse de la ressource
        WebTarget bookResource = client.target(url);

        // définition du point d'accès
        WebTarget bookEndpoint = bookResource.path("api/users/"+username);

        // ajout de l'en-tête d'autorisation et spécification du type de réponse attendu
        String token = "token"; // replace with your actual token
        Invocation.Builder builder = bookEndpoint.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        // envoi de la requête et récupération de la réponse
        Response response = builder.get();

        // si le livre a bien été trouvé, conversion du JSON en User
        if (response.getStatus() == 200) {
            myUser = response.readEntity(User.class);
        }

        // fermeture de la connexion
        client.close();

        return myUser;
    }
}
