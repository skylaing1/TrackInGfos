package org.example.database;

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

            // Beginne die Transaktion außerhalb des Try-with-Resources-Blocks
            Transaction transaction = session.beginTransaction();

            try {
                LoginData loginData = new LoginData();
                loginData.setEmail(email);
                loginData.setPasswort(password);
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

    public static Mitarbeiter getMitarbeiterByPersonalNummer(int personalNummer) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            // Finde den Mitarbeiter anhand der Personalnummer
            Mitarbeiter mitarbeiter = session.get(Mitarbeiter.class, personalNummer);

            // Committe die Transaktion
            session.getTransaction().commit();

            return mitarbeiter;
        } catch (Exception e) {
            // Handle Exceptions
            e.printStackTrace();
        }

        return null; // Rückgabe, wenn etwas schief geht
    }
}