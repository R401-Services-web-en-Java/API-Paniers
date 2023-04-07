package fr.univamu.iut.apipaniers.apis.user;

import fr.univamu.iut.apipaniers.beans.User;

public interface UserRepositoryInterface {
    public void close();

    public User getUser(String username) ;
}
