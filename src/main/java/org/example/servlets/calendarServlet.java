package org.example.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.LocalDateSerializer;
import org.example.entities.Entry;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "calendarServlet", value = "/calendar")
public class calendarServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session1 = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session1.getAttribute("SessionMitarbeiter");
        int personalNummer = mitarbeiter.getPersonalNummer();
        // Fetch entries from the database
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<Entry> entries = session.createQuery("from Entry where mitarbeiter.personalNummer = :nummer", Entry.class )
                .setParameter("nummer", personalNummer)
                .list();

        session.close();

        // Convert entries to JSON
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        String entriesJson = gson.toJson(entries);

        // Add JSON to request
        request.setAttribute("entries", entriesJson);

        request.getRequestDispatcher("WEB-INF/calendar.jsp").forward(request, response);
    }
}
