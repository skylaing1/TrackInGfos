package org.example.database;

import org.example.Alert;
import org.example.ServerService;
import org.example.ServletUtil;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DaysDAO {
    public static List<Days> fetchDaysByMitarbeiter(int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            List<Days> days = session.createQuery("from Days where mitarbeiter.personalNummer = :nummer", Days.class)
                    .setParameter("nummer", personalNummer)
                    .list();
            for (Days day : days) {
                switch (day.getStatus()) {
                    case "Urlaub" -> day.setColor("blue");
                    case "Krank" -> day.setColor("var(--bs-warning)");
                    case "Anwesend" -> day.setColor("var(--bs-success)");
                    case "Abwesend" -> day.setColor("var(--bs-danger)");
                    case "Dienstreise" -> day.setColor("darkgreen");
                }
                day.setSickHours(day.getSickDuration() / 60);
                day.setPresentHours(day.getPresentDuration() / 60);
            }

            return days;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveDaysList(List<Days> daysList) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                for (Days day : daysList) {
                    switch (day.getStatus()) {
                        case "Anwesend":
                            day.setPresentDuration(480);
                            break;
                        case "Krank":
                            day.setSickDuration(480);
                            break;
                        case "Dienstreise":
                            day.setPresentDuration(600);
                            break;
                    }
                    session.persist(day);
                }

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

    public static Alert updateDayAndReplaceEntries(int daysId, String status, String startDateStr, String entrieDesc, Mitarbeiter mitarbeiter, String description) {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();


            Days day = session.get(Days.class, daysId);
            day.setStatus(status);
            day.setDate(Date.valueOf(startDateStr));
            day.setMitarbeiter(mitarbeiter);
            day.setDescription(description);

            List<Entries> entriesList = ServletUtil.createEntriesForDay(day, status, entrieDesc, new ArrayList<>());

            session.createQuery("delete from Entries where day.daysId = :id")
                    .setParameter("id", daysId)
                    .executeUpdate();

            int totalPresentDuration = 0;
            int totalSickDuration = 0;

            for (Entries entry : entriesList) {
                if (entry.getState().equals("Anwesend") || entry.getState().equals("Dienstreise")) {
                    totalPresentDuration += entry.getEntryDuration();
                }
                if (entry.getState().equals("Krank")) {
                    totalSickDuration += entry.getEntryDuration();
                }
                session.save(entry);
            }

            day.setSickDuration(totalSickDuration);
            day.setPresentDuration(totalPresentDuration);

            session.update(day);
            session.getTransaction().commit();
            session.close();

            return Alert.successAlert("Erfolgreich", "Der Tag wurde erfolgreich aktualisiert");
        } catch (Exception e) {
            e.printStackTrace();
            return Alert.dangerAlert("Datenbankfehler", "Es ist ein Fehler beim Speichern der Daten aufgetreten");
        }
    }

    public static Days fetchDayByDateAndMitarbeiter(LocalDate date, int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            return session.createQuery("from Days where date = :date and mitarbeiter.personalNummer = :nummer", Days.class)
                    .setParameter("date", Date.valueOf(date))
                    .setParameter("nummer", personalNummer)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Days> getDaysofCurrentYear(int personalNummer) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            LocalDate date = LocalDate.now();

            return session.createQuery("from Days where year(date) = :year and mitarbeiter.personalNummer = :nummer", Days.class)
                    .setParameter("year", date.getYear())
                    .setParameter("nummer", personalNummer)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Alert deleteDayAndEntries(int daysId) {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();


            session.createQuery("delete from Days where daysId = :id")
                    .setParameter("id", daysId)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();

            return Alert.successAlert("Erfolgreich", "Der Tag wurde erfolgreich gelöscht");
        } catch (Exception e) {
            e.printStackTrace();
            return Alert.dangerAlert("Datenbankfehler", "Es ist ein Fehler beim Löschen der Daten aufgetreten");
        }
    }


    public static Days saveDay(Days day) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.persist(day);

                transaction.commit();

                return day;
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateDay(Days day) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.merge(day);

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