package org.example.database;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.Alert;
import org.example.ServerService;
import org.example.ServletUtil;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DaysDAO {
    public static List<Days> fetchDaysByMitarbeiter(int personalNummer) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Days> days = session.createQuery("from Days where mitarbeiter.personalNummer = :nummer", Days.class)
                .setParameter("nummer", personalNummer)
                .list();
        for (Days day : days) {
            if (day.getStatus().equals("Urlaub")) {
                day.setColor("blue");
            } else if (day.getStatus().equals("Krank")) {
                day.setColor("var(--bs-warning)");
            } else if (day.getStatus().equals("Anwesend")) {
                day.setColor("var(--bs-success)");
            } else if (day.getStatus().equals("Abwesend")) {
                day.setColor("var(--bs-danger)");
            } else if (day.getStatus().equals("Dienstreise")) {
                day.setColor("darkgreen");
            }
            day.setSickHours(day.getSickDuration() / 60);
            day.setPresentHours(day.getPresentDuration() / 60);
        }

        session.close();
        return days;
    }

    public static void saveDaysList(List<Days> daysList) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

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
            session.save(day);
        }

        session.getTransaction().commit();
        session.close();
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

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Days day = session.createQuery("from Days where date = :date and mitarbeiter.personalNummer = :nummer", Days.class)
                .setParameter("date", Date.valueOf(date))
                .setParameter("nummer", personalNummer)
                .uniqueResult();

        session.getTransaction().commit();
        session.close();

        return day;
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


}