package org.example.database;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.List;

public class EntriesDAO {
    public static void replaceEntriesForDay(Days day, List<Entries> entriesList) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Entries> entries = session.createQuery("from Entries where day.daysId = :dayId", Entries.class)
                .setParameter("dayId", day.getDayId())
                .list();

        for (Entries entry : entries) {
            session.delete(entry);
        }

        for (Entries entry : entriesList) {
            session.save(entry);
        }

        session.getTransaction().commit();
        session.close();
    }

    public static void createEntriesFromList(List<Entries> entriesList) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (Entries entry : entriesList) {
            session.save(entry);
        }

        session.getTransaction().commit();
        session.close();
    }

    public static List<Entries> getTodayEntriesForMitarbeiter(Mitarbeiter mitarbeiter, LocalDate date) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //Asc damit der Erste Eintrag der erste ist
        List<Entries> entries = session.createQuery("from Entries where day.mitarbeiter.personalNummer = :personalNummer and day.date = :date ORDER BY startTime ASC", Entries.class)
                .setParameter("personalNummer", mitarbeiter.getPersonalNummer())
                .setParameter("date", date)
                .list();


        session.getTransaction().commit();
        session.close();


        return entries;
    }

    public static int calculateDuration(String startTime, String endTime) {
        int startHour = Integer.parseInt(startTime.substring(0, 2));
        int startMinute = Integer.parseInt(startTime.substring(3, 5));
        int endHour = Integer.parseInt(endTime.substring(0, 2));
        int endMinute = Integer.parseInt(endTime.substring(3, 5));

        int duration;

        return duration = (endHour * 60 + endMinute) - (startHour * 60 + startMinute);
    }

    public static void deleteSingleEntry(int id) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Entries entry = session.get(Entries.class, id);
        session.delete(entry);

        session.getTransaction().commit();
        session.close();
    }
}
