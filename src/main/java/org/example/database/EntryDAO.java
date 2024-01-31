package org.example.database;

import org.example.entities.Entry;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Date;
import java.util.List;

public class EntryDAO {
    public static List<Entry> fetchEntryByMitarbeiter(int personalNummer) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Entry> entries = session.createQuery("from Entry where mitarbeiter.personalNummer = :nummer", Entry.class )
                .setParameter("nummer", personalNummer)
                .list();

        session.close();
        return entries;
    }

    public static void saveOrUpdateEntry(int entryId, String status, String startDate, String endDate, String startTime, String endTime, String description, Mitarbeiter mitarbeiter) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Entry entry = null;

        if (entryId != 0) {
            entry = session.get(Entry.class, entryId);
        }

        if (entryId == 0) {
            entry = new Entry();
        }


        entry.setStatus(status);
        entry.setStartDate(Date.valueOf(startDate));
        entry.setEndDate(Date.valueOf(endDate));
        entry.setStartTime(startTime);
        entry.setEndTime(endTime);
        entry.setDescription(description);
        entry.setMitarbeiter(mitarbeiter);

        session.saveOrUpdate(entry);
        session.getTransaction().commit();
        session.close();

    }
}