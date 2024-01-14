package org.example.database;


import org.mindrot.jbcrypt.BCrypt;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class register_service {

    public static void registerUser(String email, String password, Mitarbeiter mitarbeiter) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            Transaction transaction = session.beginTransaction();

            try {

                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

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

    public static boolean comparePassword(String password, String password2) {
        return password.equals(password2);
    }



}