package fr.univamu.iut.apipaniers.user;

public interface UserRepositoryInterface {
    public void close();

    public User getUser(String username) ;
}
