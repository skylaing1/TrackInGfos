package org.example.database;

import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public static List<Mitarbeiter> fetchAllMitarbeiterForTable() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();



            List<Mitarbeiter> mitarbeiterList = session.createQuery("FROM Mitarbeiter").list();


            for (Mitarbeiter mitarbeiter : mitarbeiterList) {
                mitarbeiter.setVorname(capitalizeFirstLetter(mitarbeiter.getVorname()));
                mitarbeiter.setName(capitalizeFirstLetter(mitarbeiter.getName()));

                java.sql.Date geburtsdatum = Date.valueOf(mitarbeiter.getGeburtsdatum());
                LocalDate localDate = geburtsdatum.toLocalDate();

                //Formatiert das Datum in dd.MM.yyyy (z.B. 01.01.2000)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate = localDate.format(formatter);
            }


            session.getTransaction().commit();

            return mitarbeiterList;

        } catch (Exception e) {
            // Handle Exceptions
            e.printStackTrace();
        }
        return null;
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

}





