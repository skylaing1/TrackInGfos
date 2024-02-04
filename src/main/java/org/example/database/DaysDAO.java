package org.example.database;

import org.example.entities.Days;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
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
                day.setColor("orange");
            } else if (day.getStatus().equals("Anwesend")) {
                day.setColor("green");
            } else if (day.getStatus().equals("Abwesend")) {
                day.setColor("red");
            } else if (day.getStatus().equals("Dienstreise")) {
                day.setColor("lightgreen");
            }

        }

        session.close();
        return days;
    }

    public static void saveDaysList(List<Days> daysList) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (Days day : daysList) {
            session.save(day);
        }

        session.getTransaction().commit();
        session.close();
    }

    public static void updateDay(int daysId, String status, String startDateStr, Mitarbeiter mitarbeiter) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Days day = session.get(Days.class, daysId);
        day.setStatus(status);
        day.setDate(Date.valueOf(startDateStr));
        day.setMitarbeiter(mitarbeiter);

        session.update(day);

        session.getTransaction().commit();
        session.close();
    }
}

