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
import org.example.database.EntryDAO;
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


       List<Entry> entries = EntryDAO.fetchEntryByMitarbeiter(personalNummer);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        String entriesJson = gson.toJson(entries);



        request.setAttribute("entries", entriesJson);

        request.getRequestDispatcher("WEB-INF/calendar.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String entryIdStr = request.getParameter("entry-id");
        String status = request.getParameter("input_status");
        String startDate = request.getParameter("input_datum_von");
        String endDate = request.getParameter("input_datum_bis");
        String startTime = request.getParameter("input_uhrzeit_von");
        String endTime = request.getParameter("input_uhrzeit_bis");
        String description = request.getParameter("input_notizen");

        HttpSession session1 = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session1.getAttribute("SessionMitarbeiter");

        int entryId;

        if (entryIdStr == null || entryIdStr.isEmpty()) {
            entryId = 0;
        } else {
            entryId = Integer.parseInt(entryIdStr);
        }

        System.out.println("id: " + entryId);
        System.out.println("status: " + status);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);
        System.out.println("startTime: " + startTime);
        System.out.println("endTime: " + endTime);
        System.out.println("description: " + description);

        EntryDAO.saveOrUpdateEntry(entryId, status, startDate, endDate, startTime, endTime, description, mitarbeiter);




        response.sendRedirect("/calendar");

    }




}
