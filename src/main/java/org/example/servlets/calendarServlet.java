package org.example.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Alert;
import org.example.LocalDateSerializer;
import org.example.ServletUtil;
import org.example.database.DaysDAO;
import org.example.database.MitarbeiterDAO;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.Mitarbeiter;

import java.io.IOException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.database.EntriesDAO;
@WebServlet(name = "calendarServlet", value = "/calendar")
public class calendarServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        Alert alert = (Alert) session.getAttribute("alert");

        request.setAttribute("alert", alert);
        session.removeAttribute("alert");



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

        String entrieDescription = "Automatisch generierte Einträge";

        String description = request.getParameter("input_notizen");
        String daysIdStr = request.getParameter("entry-id");
        String status = request.getParameter("input_status");
        String startDateStr = request.getParameter("input_datum_von");
        String endDateStr = request.getParameter("input_datum_bis");

        HttpSession session1 = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session1.getAttribute("SessionMitarbeiter");

        // Konvertieren der Strings in LocalDate
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);

        // Wenn Update eines Tages
        if (daysIdStr != null && !daysIdStr.isEmpty()) {
            if (startDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                if (status.equals("Urlaub") && mitarbeiter.getVerbleibendeUrlaubstage() < 1) {
                    Alert alert = Alert.dangerAlert("Zu wenig Urlaubstage", "Sie haben nicht genügend Urlaubstage");
                    session1.setAttribute("alert", alert);
                    response.sendRedirect("/calendar");
                    return;
                }
                int daysId = Integer.parseInt(daysIdStr);
                Alert alert = DaysDAO.updateDayAndReplaceEntries(daysId, status, startDateStr ,entrieDescription, mitarbeiter, description);
                session1.setAttribute("alert", alert);
                response.sendRedirect("/calendar");
                return;
            }
            Alert alert = Alert.dangerAlert("Fehler", "Der Tag darf nicht auf einen Sonntag fallen");
            response.sendRedirect("/calendar");
            return;
        }



        List<Days> daysList = new ArrayList<>();



        // Erstellen der Tage für den Zeitraum
        startDate.datesUntil(endDate.plusDays(1)).forEach(date -> {
            if (date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            Days day = new Days();
            day.setStatus(status);
            day.setDate(Date.valueOf(date.toString()));
            day.setMitarbeiter(mitarbeiter);
            day.setDescription(description);
            entriesList.addAll(ServletUtil.createEntriesForDay(day, status, entrieDescription, entriesList));
            daysList.add(day);
            }
        });



            for (Days day : daysList) {
                switch (day.getStatus()) {
                    case "Anwesend":
                        mitarbeiter.setWeekHoursProgress(mitarbeiter.getWeekHoursProgress() + 8);
                        day.setPresentDuration(480);
                        break;
                    case "Krank":
                        mitarbeiter.setWeekHoursProgress(mitarbeiter.getWeekHoursProgress() + 8);
                        day.setSickDuration(480);
                        break;
                    case "Dienstreise":
                        mitarbeiter.setWeekHoursProgress(mitarbeiter.getWeekHoursProgress() + 8);
                        day.setPresentDuration(600);
                        break;
                    case "Urlaub":
                        mitarbeiter.setWeekHoursProgress(mitarbeiter.getWeekHoursProgress() + 8);
                        mitarbeiter.setVerbleibendeUrlaubstage(mitarbeiter.getVerbleibendeUrlaubstage() - 1);
                        break;
                }
            }
        if (mitarbeiter.getVerbleibendeUrlaubstage() < 0) {
            mitarbeiter.setVerbleibendeUrlaubstage(0);
            Alert alert = Alert.dangerAlert("Zu wenig Urlaubstage", "Sie haben nicht genügend Urlaubstage");
            session1.setAttribute("alert", alert);
            response.sendRedirect("/calendar");
            return;
        }
            MitarbeiterDAO.updateMitarbeiter(mitarbeiter);
            // Speichern der Tage in der Datenbank
            DaysDAO.saveDaysList(daysList);
            EntriesDAO.saveEntriesFromList(entriesList);



        response.sendRedirect("/calendar");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int daysId = Integer.parseInt(request.getParameter("id"));
        DaysDAO.deleteDayAndEntries(daysId);
    }



}
