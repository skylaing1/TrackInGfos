package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.DailyStats;
import org.example.ServletUtil;
import org.example.database.DaysTransaction;
import org.example.database.EntriesTransaction;
import org.example.database.MitarbeiterTransaction;
import org.example.entities.Days;
import org.example.entities.Entries;


import java.io.IOException;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.example.Alert;
import org.example.entities.Mitarbeiter;

@WebServlet(name = "dashboardServlet", value = "/dashboard")
public class dashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        LocalDate today = LocalDate.now();

        int stundenKontingentInMinuten = mitarbeiter.getWochenstunden() * 60;
        int stundenKontingentInStunden = 0;
        int geleisteteMinutenInProzent = 0;
        int geleisteteMinuten = 0;
        int geleisteteStunden = 0;
        int krankMinuten = 0;

        int urlaubAnspruch = 28;
        int krankTage = 0;


        // Listen zum Sortieren der Tage (Bar Chart)
        List<Days> MondayList = new ArrayList<>();
        List<Days> TuesdayList = new ArrayList<>();
        List<Days> WednesdayList = new ArrayList<>();
        List<Days> ThursdayList = new ArrayList<>();
        List<Days> FridayList = new ArrayList<>();
        List<Days> SaturdayList = new ArrayList<>();

        // Arrays für die Statisken der Wochentage (Bar Chart)
        String presentHoursArray = "[";
        String sickHoursArray = "[";
        String absentHoursArray = "[";
        String breakHoursArray = "[";

        int totalPresentHours = 0;
        int totalSickHours = 0;
        int totalAbsentHours = 0;
        int totalBreakHours = 0;




        // Liste für die Tage im ganzen Jahr
        List<Days> daysList = DaysTransaction.getDaysofCurrentYear(mitarbeiter.getPersonalNummer());

        if (daysList == null) {
            request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
            return;
        }

        List<Entries> entriesList = ServletUtil.getCurrentEntriesForDashboard(mitarbeiter,daysList, session);
        session.setAttribute("entriesSession", entriesList);



        // Schliefe für die Tage im ganzen Jahr
        for (Days day : daysList) {
            // Wochentag aus Datum extrahieren und in String umwandeln
            LocalDate date = day.getDate().toLocalDate();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String dayOfWeekString = dayOfWeek.toString();

            if (ServletUtil.isInCurrentWeek(date, today) && (day.getStatus().equals("Anwesend") || day.getStatus().equals("Krank") ||  day.getStatus().equals("Urlaub") || day.getStatus().equals("Dienstreise"))) {
                if (day.getStatus().equals("Anwesend") ||  day.getStatus().equals("Dienstreise")) {
                    geleisteteMinuten += day.getPresentDuration();
                }
                if (day.getStatus().equals("Urlaub")) {
                    stundenKontingentInMinuten -= 480;
                }
                if (day.getStatus().equals("Krank")) {
                    stundenKontingentInMinuten -= day.getSickDuration();
                }
            }

            // Tage in die Listen ordnen
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

            // Urlaubstage und Krankheitstage zählen
            if (day.getStatus().equals("Urlaub")) {
                urlaubAnspruch--;
            }
            if (day.getStatus().equals("Krank")) {
                krankMinuten += day.getSickDuration();
            }
        }

        // Statistiken aus den Listen berechnen und in eine Liste neue Liste packen für die Bar Chart
        List<DailyStats> dailyStatsList = ServletUtil.getDailyStatsList(MondayList, TuesdayList, WednesdayList, ThursdayList, FridayList, SaturdayList);

        // Schleife für die Formatierung der Arrays für die Bar Chart und die Summe der Stunden für die Doughnut Chart
        for (int i = 0; i < dailyStatsList.size(); i++) {
            // Summe der Stunden für die Doughnut Chart
            totalPresentHours += dailyStatsList.get(i).getPresentDuration();
            totalSickHours += dailyStatsList.get(i).getSickDuration();
            totalAbsentHours += dailyStatsList.get(i).getAbsentDuration();
            totalBreakHours += dailyStatsList.get(i).getBreakDuration();

            // Formatierung der Arrays für die Bar Chart
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

        String pieChartArray = "[" + totalPresentHours + "," +  totalBreakHours+ "," + totalAbsentHours + "," + totalSickHours + "]";



        // Berechnung der geleisteten Stunden für die Progress Bar
        if (geleisteteMinuten > 0 && stundenKontingentInMinuten > 0){
            geleisteteMinutenInProzent = (geleisteteMinuten * 100) / stundenKontingentInMinuten;
            geleisteteStunden = geleisteteMinuten / 60;
            stundenKontingentInStunden = stundenKontingentInMinuten / 60;
        }

        //krank tage berechnen
        if (krankMinuten > 0){
            krankTage = krankMinuten / 480;
        }


        // Update der geleisteten Stunden und des Urlaubsanspruchs in der Datenbank
        MitarbeiterTransaction.updateWeekHoursProgressAndVacationDays(geleisteteStunden, urlaubAnspruch , mitarbeiter);




        Alert alert = (Alert) session.getAttribute("alert");
        request.setAttribute("alert", alert);
        session.removeAttribute("alert");

        request.setAttribute("entries", entriesList);

        request.setAttribute("presentHoursArray", presentHoursArray);
        request.setAttribute("sickHoursArray", sickHoursArray);
        request.setAttribute("absentHoursArray", absentHoursArray);
        request.setAttribute("breakHoursArray", breakHoursArray);

        request.setAttribute("pieChartArray", pieChartArray);

        request.setAttribute("krankTage", krankTage);
        request.setAttribute("geleisteteStunden", geleisteteStunden);
        request.setAttribute("stundenKontingentInStunden", stundenKontingentInStunden);
        request.setAttribute("geleisteteStundenInProzent", geleisteteMinutenInProzent);
        request.setAttribute("VerbleibenderUrlaubAnspruch", urlaubAnspruch);
        request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDate date = LocalDate.now();
      HttpSession session = request.getSession(false);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");
        boolean newDay = false;

      String state = request.getParameter("input_status");
      String startTime = request.getParameter("input_zeit_von");
      String endTime = request.getParameter("input_zeit_bis");
      String description = request.getParameter("input_notizen");
      List<Entries> entriesList = (List<Entries>) session.getAttribute("entriesSession");
      session.removeAttribute("entriesSession");

      if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
          Alert alert = Alert.dangerAlert("Ungültiger Eintrag", "Es können keine Einträge für Sonntage gemacht werden");
          session.setAttribute("alert", alert);
          response.sendRedirect("/dashboard");
          return;
      }



        LocalTime newEntryStart = LocalTime.parse(startTime, formatter);
        LocalTime newEntryEnd = LocalTime.parse(endTime, formatter);

        // Wenn der neue Eintrag sich mit einem bestehenden Eintrag überschneidet, wird ein Alert generiert und der Nutzer wird zurück zum Dashboard geleitet
      if (entriesList != null && !entriesList.isEmpty()) {
          for (Entries entry : entriesList) {
              LocalTime entryStart = LocalTime.parse(entry.getStartTime(), formatter);
              LocalTime entryEnd = LocalTime.parse(entry.getEndTime(), formatter);
              if (newEntryStart.isAfter(entryStart) && newEntryStart.isBefore(entryEnd) || newEntryEnd.isAfter(entryStart) && newEntryEnd.isBefore(entryEnd)) {
                  Alert alert = Alert.dangerAlert( "Ungültiger Eintrag","Der neue Eintrag überschneidet sich mit einem bestehenden Eintrag, bitte überprüfen Sie die Zeiten und versuchen Sie es erneut.");
                  session.setAttribute("alert", alert);
                  response.sendRedirect("/dashboard");
                  return;
              }
          }
      } else {
          entriesList = new ArrayList<>();
        }

        Entries entry = new Entries();
        entry.setStatus(state);
        entry.setStartTime(startTime);
        entry.setEndTime(endTime);
        entry.setDescription(description);
        entry.setEntryDuration(ServletUtil.calculateDuration(startTime, endTime));

        Days day = DaysTransaction.fetchDayByDateAndMitarbeiter(date, mitarbeiter.getPersonalNummer());

        if (day == null) {
            day = new Days();
            day.setDate(Date.valueOf(date));
            day.setMitarbeiter(mitarbeiter);
            day.setDescription("Automatisch generiert");
            day.setStatus(state);

            newDay = true;
        }




        if (entry.getState().equals("Anwesend") || entry.getState().equals("Dienstreise")) {
            int currentDuration = day.getPresentDuration();
            day.setPresentDuration(currentDuration + entry.getEntryDuration());
        }
        if (entry.getState().equals("Krank")) {
            int currentDuration = day.getSickDuration();
            day.setSickDuration(currentDuration + entry.getEntryDuration());
        }




        if (!newDay) {
            day.setStatus(EntriesTransaction.findStatusWithLargestSum(mitarbeiter.getPersonalNummer(), date));
            entry.setDay(day);
            entriesList.add(entry);
            day.setEntries(entriesList);
            DaysTransaction.updateDay(day);

        } else {
            Days savedDay = DaysTransaction.saveDay(day);
            entry.setDay(day);
            entriesList.add(entry);
            savedDay.setEntries(entriesList);
            DaysTransaction.updateDay(savedDay);
        }

        Alert alert = Alert.successAlert("Erfolgreich", "Der Eintrag wurde erfolgreich hinzugefügt");

      session.setAttribute("alert", alert);
      response.sendRedirect("/dashboard");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        EntriesTransaction.deleteSingleEntry(id);
    }
}