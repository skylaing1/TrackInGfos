package org.example.database;

import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.example.entities.LoginData;
import org.mindrot.jbcrypt.BCrypt;

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

    public static void registerLoginData(String email, String hashedPassword, Mitarbeiter mitarbeiter) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            Transaction transaction = session.beginTransaction();

            try {

                LoginData loginData = new LoginData();
                loginData.setEmail(email.toLowerCase());
                loginData.setPasswort(hashedPassword);
                loginData.setMitarbeiter(mitarbeiter);

                session.save(loginData);

                // Committe die Transaktion, wenn keine Exception auftritt
                transaction.commit();
            } catch (Exception e) {
                // Rollback der Transaktion bei einem Fehler
                transaction.rollback();
                // Handle Exceptions
                e.printStackTrace();
            }
        } catch (Exception e) {
            // Handle Exceptions beim Öffnen/Schließen der Session oder beim Starten/Rollback der Transaktion
            e.printStackTrace();
        }
    }

    // Weitere Methoden für Datenbankoperationen mit LoginData können hier hinzugefügt werden
}
