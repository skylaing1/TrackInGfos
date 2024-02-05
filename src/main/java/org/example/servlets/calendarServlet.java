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
import org.example.ServletUtil;
import org.example.database.DaysDAO;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.Mitarbeiter;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.database.EntriesDAO;
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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Liste für die Einträge
        List<Entries> entriesList = new ArrayList<>();

        String description = request.getParameter("input_description");
        String daysIdStr = request.getParameter("entry-id");
        String status = request.getParameter("input_status");
        String startDateStr = request.getParameter("input_datum_von");
        String endDateStr = request.getParameter("input_datum_bis");

        HttpSession session1 = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session1.getAttribute("SessionMitarbeiter");

        if (description == null || description.isEmpty()) {
            description = "Automatisch generierte Einträge";
        }


        if (daysIdStr != null &&  !daysIdStr.isEmpty()) {
            int daysId = Integer.parseInt(daysIdStr);
            Days day = DaysDAO.updateDayAndReturn(daysId, status, startDateStr, mitarbeiter);
            entriesList = ServletUtil.createEntriesForDay(day, status, description, entriesList);
            EntriesDAO.replaceEntriesForDay(day, entriesList);
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

        List<Days> daysList = new ArrayList<>();



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
