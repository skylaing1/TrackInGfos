package org.example.database;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.Alert;
import org.example.ServletUtil;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;

public class UtilDAO {

    public static Alert createEntryAndUpdateDay(String state, String startTime, String endTime, String description, LocalDate date, HttpServletRequest request) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            HttpSession session1 = request.getSession(false);
            Mitarbeiter mitarbeiter = (Mitarbeiter) session1.getAttribute("SessionMitarbeiter");

            Days day = DaysDAO.fetchDayByDateAndMitarbeiter(date, mitarbeiter.getPersonalNummer());

            if (day == null) {
                day = new Days();
                day.setDate(java.sql.Date.valueOf(date));
                day.setMitarbeiter(mitarbeiter);
                day.setStatus(state);
                day.setDescription("Automatisch generierter Tag");
                session.save(day);
            }

            Entries entry = new Entries();
            entry.setStatus(state);
            entry.setDay(day);
            entry.setStartTime(startTime);
            entry.setEndTime(endTime);
            entry.setDescription(description);
            int duration = EntriesDAO.calculateDuration(startTime, endTime);
            entry.setEntryDuration(duration);

            if (entry.getState().equals("Anwesend") || entry.getState().equals("Dienstreise")) {
                int currentDuration = day.getPresentDuration();
                day.setPresentDuration(currentDuration + duration);
            }
            if (entry.getState().equals("Krank")) {
                int currentDuration = day.getSickDuration();
                day.setSickDuration(currentDuration + duration);
            }


            session.save(entry);

            String hql = "SELECT e.state " +
                    "FROM Entries e " +
                    "WHERE e.day.daysId = :daysId " +
                    "GROUP BY e.state " +
                    "ORDER BY SUM(e.entryDuration) DESC";
            Query query = session.createQuery(hql);
            query.setParameter("daysId", day.getDayId());
            query.setMaxResults(1);
            String result = (String) query.uniqueResult();

            if (!day.getStatus().equals(result)) {
                day.setStatus(result);
            }
            session.update(day);

            session.getTransaction().commit();

            return Alert.successAlert("Erfolgreich", "Eintrag erfolgreich gespeichert");
        } catch (Exception e) {
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
            e.printStackTrace();
            return Alert.dangerAlert("Datenbankfehler", "Es ist ein Fehler beim Speichern der Daten aufgetreten");
        } finally {
            session.close();
        }
    }
}
