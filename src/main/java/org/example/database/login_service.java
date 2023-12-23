package org.example.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.example.entities.LoginData;
import org.mindrot.jbcrypt.BCrypt;

public class login_service {

    public static boolean authenticateUser(String email, String password) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            // Benutzer mit der gegebenen E-Mail-Adresse aus der Datenbank abrufen
            LoginData loginData = (LoginData) session.createQuery("FROM LoginData WHERE email = :email")
                    .setParameter("email", email.toLowerCase())
                    .uniqueResult();

            session.getTransaction().commit();

            if (loginData != null) {
                // Eingegebenes Passwort verschlüsseln und mit dem in der Datenbank gespeicherten Passwort vergleichen
                return BCrypt.checkpw(password, loginData.getPasswort());
            } else {
                return false; // Benutzer mit der gegebenen E-Mail-Adresse nicht gefunden
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Fehler beim Login
        }
    }
}
