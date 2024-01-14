package org.example.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.example.entities.LoginData;

public class LoginDataDAO {

    public static LoginData getLoginDataByEmail(String email) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            // LoginData mit der gegebenen E-Mail-Adresse aus der Datenbank abrufen
            LoginData loginData = (LoginData) session.createQuery("FROM LoginData WHERE email = :email")
                    .setParameter("email", email.toLowerCase())
                    .uniqueResult();

            session.getTransaction().commit();

            return loginData;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Fehler beim Abrufen von LoginData
        }
    }

    // Weitere Methoden für Datenbankoperationen mit LoginData können hier hinzugefügt werden
}
