package fr.univamu.iut.apipaniers.beans;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    public void testConstructorAndGetters() {
        String username = "testuser";
        String firstname = "John";
        String lastname = "Doe";
        String mail = "johndoe@example.com";
        String password = "password123";
        String role = "admin";

        User user = new User(username, firstname, lastname, mail, password, role);

        assertEquals(username, user.getUsername());
        assertEquals(firstname, user.getFirstname());
        assertEquals(lastname, user.getLastname());
        assertEquals(mail, user.getMail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }

    @Test
    public void testSetters() {
        User user = new User();

        String username = "testuser";
        String firstname = "John";
        String lastname = "Doe";
        String mail = "johndoe@example.com";
        String password = "password123";
        String role = "admin";

        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setMail(mail);
        user.setPassword(password);
        user.setRole(role);

        assertEquals(username, user.getUsername());
        assertEquals(firstname, user.getFirstname());
        assertEquals(lastname, user.getLastname());
        assertEquals(mail, user.getMail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }
}