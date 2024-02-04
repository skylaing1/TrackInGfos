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
import org.example.database.DaysDAO;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "calendarServlet", value = "/calendar")
public class calendarServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session1 = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session1.getAttribute("SessionMitarbeiter");
        int personalNummer = mitarbeiter.getPersonalNummer();


       List<Days> dayslist = DaysDAO.fetchDaysByMitarbeiter(personalNummer);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        String daysJson = gson.toJson(dayslist);



        request.setAttribute("days", daysJson);

        request.getRequestDispatcher("WEB-INF/calendar.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String daysIdStr = request.getParameter("entry-id");
        String status = request.getParameter("input_status");
        String startDateStr = request.getParameter("input_datum_von");
        String endDateStr = request.getParameter("input_datum_bis");

        HttpSession session1 = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session1.getAttribute("SessionMitarbeiter");

        if (daysIdStr != null) {
            int daysId = Integer.parseInt(daysIdStr);
            DaysDAO.updateDay(daysId, status, startDateStr, mitarbeiter);
            response.sendRedirect("/calendar");
            return;
        }

        System.out.println("id: " + daysIdStr);
        System.out.println("status: " + status);
        System.out.println("startDate: " + startDateStr);
        System.out.println("endDate: " + endDateStr);

        // Konvertieren der Strings in LocalDate
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        // Liste für die Tage
        List<Days> daysList = new ArrayList<>();
        List<Entries> entriesList = new ArrayList<>();

        // Erstellen der Tage für den Zeitraum
        startDate.datesUntil(endDate.plusDays(1)).forEach(date -> {
            Days day = new Days();
            day.setStatus(status);
            day.setDate(Date.valueOf(date.toString()));
            day.setMitarbeiter(mitarbeiter);
            daysList.add(day);
        });

        // Speichern der Tage in der Datenbank
        DaysDAO.saveDaysList(daysList);

        response.sendRedirect("/calendar");
    }



}
