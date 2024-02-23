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
import org.hibernate.query.Query;

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
       try (Session session = ServerService.getSessionFactory().openSession()) {
           return session.createQuery("FROM Mitarbeiter", Mitarbeiter.class).list();
        } catch (Exception e) {
            e.printStackTrace();
           return null;
        }

    }

    public static void deleteSingleMitarbeiter(int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Mitarbeiter mitarbeiter = new Mitarbeiter();
                mitarbeiter.setPersonalNummer(personalNummer);
                session.remove(mitarbeiter);

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

    public static void addMitarbeiter(Mitarbeiter mitarbeiter) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                session.persist(mitarbeiter);

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

    public static void updateMitarbeiter(String vorname, int personalNummer, String nachname, String geburtsdatum, String eintrittsdatum, String position, String hashedPassword, int wochenstunden, boolean admin) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Mitarbeiter mitarbeiter = new Mitarbeiter();

                mitarbeiter.setVorname(vorname);
                mitarbeiter.setPersonalNummer(personalNummer);
                mitarbeiter.setName(nachname);
                mitarbeiter.setGeburtsdatum(LocalDate.parse(geburtsdatum));
                mitarbeiter.setEinstellungsdatum(LocalDate.parse(eintrittsdatum));
                mitarbeiter.setPosition(position);
                mitarbeiter.setOnetimepassword(hashedPassword);
                mitarbeiter.setWochenstunden(wochenstunden);
                mitarbeiter.setAdmin(admin);

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

    public static void deleteLoginDataAndTokens(int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Query<LoginData> query = session.createNativeQuery("DELETE FROM LoginData WHERE mitarbeiter_personalNummer = :personalnummer", LoginData.class);
                query.setParameter("personalnummer", personalNummer);
                query.executeUpdate();

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
