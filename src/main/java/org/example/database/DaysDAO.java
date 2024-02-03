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
        List<Days> days = session.createQuery("from Days where mitarbeiter.personalNummer = :nummer", Days.class )
                .setParameter("nummer", personalNummer)
                .list();

        session.close();
        return days;
    }

    public static void saveOrUpdateDays(int dayId, String status, String startDate, String endDate, Mitarbeiter mitarbeiter) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Days day = null;

        if (dayId != 0) {
             day = session.get(Days.class, dayId);
        }

        if (dayId == 0) {
            day = new Days();
        }


        day.setStatus(status);
        day.setStartDate(Date.valueOf(startDate));
        day.setEndDate(Date.valueOf(endDate));
        day.setMitarbeiter(mitarbeiter);

        session.saveOrUpdate(day);
        session.getTransaction().commit();
        session.close();

    }
}