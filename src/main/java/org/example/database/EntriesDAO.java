package org.example.database;

import org.example.entities.Days;
import org.example.entities.Entries;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
}
