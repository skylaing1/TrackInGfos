package org.example.database;

import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.example.entities.Mitarbeiter;

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

                String formattedWochenstunden = mitarbeiter.getWochenstunden() + " h";

                java.sql.Date geburtsdatum = Date.valueOf(mitarbeiter.getGeburtsdatum());
                LocalDate  localDate_Geburtsdatum = geburtsdatum.toLocalDate();
                LocalDate  localDate_Einstellungsdatum = mitarbeiter.getEintrittsdatum();

                //Formatiert das Datum in dd.MM.yyyy (z.B. 01.01.2000)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate_EinstellDatum = localDate_Einstellungsdatum.format(formatter);
                String formattedDate_GebDatum = localDate_Geburtsdatum.format(formatter);

                mitarbeiter.setFormattedWochenstunden(formattedWochenstunden);
                mitarbeiter.setGeburtsdatumFormatted(formattedDate_GebDatum);
                mitarbeiter.setEinstellungsdatumFormatted(formattedDate_EinstellDatum);
                mitarbeiter.setVorname(capitalizeFirstLetter(mitarbeiter.getVorname()));
                mitarbeiter.setName(capitalizeFirstLetter(mitarbeiter.getName()));
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

    public static void deleteSingleMitarbeiter(int id) {

        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            Mitarbeiter mitarbeiter = new Mitarbeiter();
            mitarbeiter.setPersonalNummer(id);

            session.remove(mitarbeiter);

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void deleteMultipleMitarbeiter(List<Mitarbeiter> mitarbeiterList) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            for (Mitarbeiter mitarbeiter : mitarbeiterList) {
                session.remove(mitarbeiter);
            }

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProfilePicture(Mitarbeiter mitarbeiter, String fileName, String appPath) {

        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            // Check if the Mitarbeiter entity has a profile picture set
            if (!mitarbeiter.getProfilePicture().equals("default.jpeg")) {
                // Construct the path to the old profile picture
                String oldPicPath = appPath + File.separator + "resources" + File.separator + "img" + File.separator + "avatars" + File.separator + mitarbeiter.getProfilePicture();

                File oldPicFile = new File(oldPicPath);
                if (oldPicFile.exists()) {
                    oldPicFile.delete();
                }
            }

            // Update the profile picture
            mitarbeiter.setProfilePicture(fileName);

            session.update(mitarbeiter);

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
