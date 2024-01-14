package org.example.database;

import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MitarbeiterDAO {


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
