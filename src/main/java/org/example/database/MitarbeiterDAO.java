package org.example.database;

import jakarta.persistence.Tuple;
import org.example.ServerService;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.example.entities.Token;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;

import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MitarbeiterDAO {


    public static Mitarbeiter getMitarbeiterByPersonalNummer(int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            return session.get(Mitarbeiter.class, personalNummer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Mitarbeiter> fetchAllMitarbeiterForTable() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();


            List<Mitarbeiter> mitarbeiterList = session.createQuery("FROM Mitarbeiter").list();


            for (Mitarbeiter mitarbeiter : mitarbeiterList) {
                mitarbeiter.setVorname(capitalizeFirstLetter(mitarbeiter.getVorname()));
                mitarbeiter.setName(capitalizeFirstLetter(mitarbeiter.getName()));

                String formattedWochenstunden = mitarbeiter.getWochenstunden() + " h";

                mitarbeiter.setWochenstundenFormatted(formattedWochenstunden);

                java.sql.Date geburtsdatum = Date.valueOf(mitarbeiter.getGeburtsdatum());
                LocalDate  localDate_Geburtsdatum = geburtsdatum.toLocalDate();
                LocalDate  localDate_Einstellungsdatum = mitarbeiter.getEinstellungsdatum();

                //Formatiert das Datum in dd.MM.yyyy (z.B. 01.01.2000)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDate_EinstellungsDatum = localDate_Einstellungsdatum.format(formatter);
                String formattedDate_GeburtsDatum = localDate_Geburtsdatum.format(formatter);


                mitarbeiter.setGeburtsdatumFormatted(formattedDate_GeburtsDatum);
                mitarbeiter.setEinstellungsdatumFormatted(formattedDate_EinstellungsDatum);

            }

            session.getTransaction().commit();

            session.close();

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

            session.close();

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

            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProfilePicture(Mitarbeiter mitarbeiter, String fileName, String appPath) {

        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();


            if (!mitarbeiter.getProfilePicture().equals("../resources/img/avatars/default.jpeg")) {

                String oldPicPath = appPath + File.separator + "resources" + File.separator + "img" + File.separator + "avatars" + File.separator + mitarbeiter.getProfilePicture();

                File oldPicFile = new File(oldPicPath);
                if (oldPicFile.exists()) {
                    oldPicFile.delete();
                }
            }


            mitarbeiter.setProfilePicture(fileName);

            session.update(mitarbeiter);

            session.getTransaction().commit();

            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addMitarbeiter(String vorname, int personalNummer, String nachname, String geburtsdatum, String eintrittsdatum, String position, String onetimepassword, int wochenstunden, boolean admin) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            Mitarbeiter mitarbeiter = new Mitarbeiter();
            mitarbeiter.setVorname(vorname);
            mitarbeiter.setPersonalNummer(personalNummer);
            mitarbeiter.setName(nachname);
            mitarbeiter.setGeburtsdatum(LocalDate.parse(geburtsdatum));
            mitarbeiter.setEinstellungsdatum(LocalDate.parse(eintrittsdatum));
            mitarbeiter.setPosition(position);
            mitarbeiter.setOnetimepassword(onetimepassword);
            mitarbeiter.setWochenstunden(wochenstunden);
            mitarbeiter.setAdmin(admin);
            mitarbeiter.setProfilePicture("../resources/img/avatars/default.jpeg");

            session.save(mitarbeiter);

            session.getTransaction().commit();

              session.close();

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public static void updateMitarbeiter(String vorname, int personalNummer, String nachname, String geburtsdatum, String eintrittsdatum, String position, String hashedPassword, int wochenstunden, boolean admin) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
                Session session = factory.openSession()) {

                session.beginTransaction();

                Mitarbeiter mitarbeiter = session.get(Mitarbeiter.class, personalNummer);



                mitarbeiter.setVorname(vorname);
                mitarbeiter.setPersonalNummer(personalNummer);
                mitarbeiter.setName(nachname);
                mitarbeiter.setGeburtsdatum(LocalDate.parse(geburtsdatum));
                mitarbeiter.setEinstellungsdatum(LocalDate.parse(eintrittsdatum));
                mitarbeiter.setPosition(position);
                mitarbeiter.setOnetimepassword(hashedPassword);
                mitarbeiter.setWochenstunden(wochenstunden);
                mitarbeiter.setAdmin(admin);

                session.update(mitarbeiter);

                session.getTransaction().commit();

                session.close();

            } catch (Exception e) {
                e.printStackTrace();

            }


    }

    public static void deleteLoginDataAndTokens(int personalNummer) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            // Fetch the Mitarbeiter object from the database using the personalNummer
            Mitarbeiter mitarbeiter = (Mitarbeiter) session.createQuery("FROM Mitarbeiter WHERE personalNummer = :personalNummer")
                    .setParameter("personalNummer", personalNummer)
                    .uniqueResult();

            if (mitarbeiter != null) {
                // Get the LoginData object associated with the Mitarbeiter object
                LoginData loginData = mitarbeiter.getLoginData();

                if (loginData != null) {
                    // Remove the association between the Mitarbeiter and LoginData entities
                    mitarbeiter.setLoginData(null);

                    // Delete the Token objects associated with the LoginData object
                    for (Token token : loginData.getTokens()) {
                        session.delete(token);
                    }

                    // Delete the LoginData object
                    session.delete(loginData);
                }
            }

            session.getTransaction().commit();

            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateWeekHoursProgressAndVacationDays(int geleisteteMinutenInProzent, int leftVacation, Mitarbeiter mitarbeiter) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                mitarbeiter.setWeekHoursProgress(geleisteteMinutenInProzent);
                mitarbeiter.setVerbleibendeUrlaubstage(leftVacation);
                session.merge(mitarbeiter);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
