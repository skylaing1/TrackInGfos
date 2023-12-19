package org.example.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.example.entities.LoginData;

public class LoginService {

    public static boolean authenticateUser(String email, String password) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            // Benutzer mit der gegebenen E-Mail-Adresse aus der Datenbank abrufen
            LoginData loginData = (LoginData) session.createQuery("FROM LoginData WHERE email = :email")
                    .setParameter("email", email)
                    .uniqueResult();

            session.getTransaction().commit();

            if (loginData != null) {
                // Passwort abgleichen (einfach für Testzwecke)
                return password.equals(loginData.getPasswort());
            } else {
                return false; // Benutzer mit der gegebenen E-Mail-Adresse nicht gefunden
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Fehler beim Login
        }
    }
}
