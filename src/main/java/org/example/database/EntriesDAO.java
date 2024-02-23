package org.example.database;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.ServerService;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class EntriesDAO {

    public static void createEntriesFromList(List<Entries> entriesList) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                for (Entries entry : entriesList) {
                    session.persist(entry);
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


    public static String findStatusWithLargestSum(int personalNummer, LocalDate date) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Query<Entries> query = session.createQuery("from Entries where day.mitarbeiter.personalNummer = :personalNummer and day.date = :date ORDER BY startTime ASC", Entries.class);
            query.setParameter("personalNummer", personalNummer);
            query.setParameter("date", date);
            query.setMaxResults(1);

            return query.uniqueResult().getState();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteSingleEntry(int id) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                Entries entry = session.get(Entries.class, id);
                Days day = entry.getDay();

                if (entry.getState().equals("Anwesend") || entry.getState().equals("Dienstreise")) {
                    int currentDuration = day.getPresentDuration();
                    day.setPresentDuration(currentDuration - entry.getEntryDuration());
                    day.getEntries().remove(entry);
                    session.merge(day);
                } else if (entry.getState().equals("Krank")) {
                    int currentDuration = day.getSickDuration();
                    day.setSickDuration(currentDuration - entry.getEntryDuration());
                    day.getEntries().remove(entry);
                    session.merge(day);
                } else {
                    day.getEntries().remove(entry);
                }
                session.remove(entry);

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
