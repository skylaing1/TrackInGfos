package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.DailyStats;
import org.example.ServletUtil;
import org.example.database.DaysDAO;
import org.example.database.EntriesDAO;
import org.example.database.UtilDAO;
import org.example.entities.Days;
import org.example.entities.Entries;


import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import org.example.Alert;
import org.example.entities.Mitarbeiter;

@WebServlet(name = "dashboardServlet", value = "/dashboard")
public class dashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");


        int stundenKontingentInMinuten = mitarbeiter.getWochenstunden() * 60;
        int geleisteteMinutenInProzent = 0;
        int geleisteteMinuten = 0;

        int urlaubAnspruch = 28;

        // Listen zum Sortieren der Tage
        List<Days> MondayList = new ArrayList<>();
        List<Days> TuesdayList = new ArrayList<>();
        List<Days> WednesdayList = new ArrayList<>();
        List<Days> ThursdayList = new ArrayList<>();
        List<Days> FridayList = new ArrayList<>();
        List<Days> SaturdayList = new ArrayList<>();

        String presentHoursArray = "[";
        String sickHoursArray = "[";
        String absentHoursArray = "[";
        String breakHoursArray = "[";


        List<Entries> entriesList = ServletUtil.getCurrentEntriesForDashboard(request);

        // Liste für die Tage im ganzen Jahr
        List<Days> daysList = DaysDAO.getDaysofCurrentYear(request);

        // Liste für die Tage im aktuellen Monat
        List<Days> daysListCurrentMonth = ServletUtil.getDaysofCurrentMonth(request, daysList);

        // Schliefe für die Tage im ganzen Jahr
        for (Days day : daysList) {
            // Wochentag aus Datum extrahieren
            LocalDate date = day.getDate().toLocalDate();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String dayOfWeekString = dayOfWeek.toString();

            switch (dayOfWeekString) {
                case "MONDAY":
                    MondayList.add(day);
                    break;
                case "TUESDAY":
                    TuesdayList.add(day);
                    break;
                case "WEDNESDAY":
                    WednesdayList.add(day);
                    break;
                case "THURSDAY":
                    ThursdayList.add(day);
                    break;
                case "FRIDAY":
                    FridayList.add(day);
                    break;
                case "SATURDAY":
                    SaturdayList.add(day);
                    break;
            }

            if (day.getStatus().equals("Urlaub")) {
                urlaubAnspruch--;
            }
        }

        // Liste für die Statisken der Wochentage
        List<DailyStats> dailyStatsList = ServletUtil.getDailyStatsList(MondayList, TuesdayList, WednesdayList, ThursdayList, FridayList, SaturdayList);

        // Schleife für die Statisken der Wochentage
        for (int i = 0; i < dailyStatsList.size(); i++) {
            presentHoursArray += dailyStatsList.get(i).getPresentDuration();
            sickHoursArray += dailyStatsList.get(i).getSickDuration();
            absentHoursArray += dailyStatsList.get(i).getAbsentDuration();
            breakHoursArray += dailyStatsList.get(i).getBreakDuration();
            if (i < dailyStatsList.size() - 1) {
                presentHoursArray += ",";
                sickHoursArray += ",";
                absentHoursArray += ",";
                breakHoursArray += ",";
            }
        }
        presentHoursArray += "]";
        sickHoursArray += "]";
        absentHoursArray += "]";
        breakHoursArray += "]";


        // Schleife für die Tage im aktuellen Monat
        for (Days day : daysListCurrentMonth) {
            if (day.getStatus().equals("Anwesend") || day.getStatus().equals("Dienstreise")) {
                geleisteteMinuten += day.getPresentDuration();
            }
        }

        if (geleisteteMinuten > 0) {
            geleisteteMinutenInProzent = (geleisteteMinuten * 100) / stundenKontingentInMinuten;
        }




        Alert alert = (Alert) session.getAttribute("alert");

        request.setAttribute("alert", alert);
        session.removeAttribute("alert");

        request.setAttribute("entries", entriesList);

        request.setAttribute("presentHoursArray", presentHoursArray);
        request.setAttribute("sickHoursArray", sickHoursArray);
        request.setAttribute("absentHoursArray", absentHoursArray);
        request.setAttribute("breakHoursArray", breakHoursArray);

        request.setAttribute("geleisteteStundenInProzent", geleisteteMinutenInProzent);
        request.setAttribute("VerbleibenderUrlaubAnspruch", urlaubAnspruch);
        request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      HttpSession session = request.getSession(false);

      String state = request.getParameter("input_status");
      String startTime = request.getParameter("input_zeit_von");
      String endTime = request.getParameter("input_zeit_bis");
      String description = request.getParameter("input_notizen");

      LocalDate date = LocalDate.now();


      Alert alert = UtilDAO.createEntryAndUpdateDay(state, startTime, endTime, description, date, request);

      session.setAttribute("alert", alert);

      response.sendRedirect("dashboard");
    }
}

