package org.example;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.TokenDAO;
import org.example.entities.*;
import org.example.database.EntriesDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;


public class ServletUtil {

    public static List<Entries> createEntriesForDay(Days day, String state, String description, List<Entries> entriesList) {
        Entries entry = new Entries();

        switch (state) {
            case "Anwesend":
                entry.setStatus(state);
                entry.setDay(day);
                entry.setStartTime("08:00");
                entry.setEndTime("12:00");
                entry.setDescription(description);
                entry.setEntryDuration(240);
                entriesList.add(entry);
                entriesList.add(createDefaultPauseEntry(day, description));
                entry = new Entries();
                entry.setStatus(state);
                entry.setDay(day);
                entry.setStartTime("13:00");
                entry.setEndTime("17:00");
                entry.setDescription(description);
                entry.setEntryDuration(240);
                entriesList.add(entry);
                break;
            case "Krank", "Abwesend", "Urlaub":
                entriesList.add(createDefaultEntry(day, description, state));
                break;
            case "Dienstreise":
                entry.setStatus(state);
                entry.setDay(day);
                entry.setStartTime("08:00");
                entry.setEndTime("18:00");
                entry.setDescription(description);
                entry.setEntryDuration(600);
                entriesList.add(entry);
                break;
        }

        return entriesList;
    }


    public static boolean checkSessionAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        if (session == null || session.getAttribute("SessionMitarbeiter") == null) {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("rememberMe")) {
                        Token token = TokenDAO.getValidToken(cookie.getValue());
                        if (token != null) {
                            session = request.getSession(true);
                            session.setAttribute("SessionMitarbeiter", token.getLoginData().getMitarbeiter());
                            response.sendRedirect("/dashboard");
                            return true;
                        }
                        cookie.setMaxAge(0);
                        return false;
                    }
                }
            }
            return false;
        }
        response.sendRedirect("/dashboard");
        return true;
    }


    public static boolean authenticateUser(String email, String password, LoginData loginData) {

            if (loginData != null) {
                return BCrypt.checkpw(password, loginData.getPasswort());
            } else {
                return false; // Benutzer mit der gegebenen E-Mail-Adresse nicht gefunden
            }
    }

    public static Entries createDefaultPauseEntry(Days day, String description) {
        Entries entry = new Entries();
        entry.setStatus("Pause");
        entry.setDay(day);
        entry.setStartTime("12:00");
        entry.setEndTime("13:00");
        entry.setEntryDuration(60);
        entry.setDescription(description);
        return entry;
    }

    public static Entries createDefaultEntry(Days day, String description, String status) {
        Entries entry = new Entries();
        entry.setStatus(status);
        entry.setDay(day);
        entry.setStartTime("08:00");
        entry.setEndTime("16:00");
        entry.setEntryDuration(480);
        entry.setDescription(description);
        return entry;
    }


    public static List<Entries> getCurrentEntriesForDashboard(Mitarbeiter mitarbeiter , List<Days> daysList, HttpSession session) {
        LocalDate date = LocalDate.now();

        int totalDuration = 0;

        List<Entries> entriesList = new ArrayList<>();

        for (Days day : daysList) {
            if (day.getDate().toLocalDate().equals(date)) {
                entriesList = day.getEntries();
                break;
            }
        }

        if (entriesList == null) {
            return null;
        }
        for (Entries entry : entriesList) {

            // Farbe der Karte festlegen
            switch (entry.getState()) {
                case "Anwesend":
                    entry.setCardColor("var(--bs-success)");
                    break;
                case "Krank":
                    entry.setCardColor("var(--bs-warning)");
                    break;
                case "Abwesend":
                    entry.setCardColor("var(--bs-danger)");
                    break;
                case "Urlaub":
                    entry.setCardColor("var(--bs-info)");
                    break;
                case "Dienstreise":
                    entry.setCardColor("#014421");
                    break;
                case "Pause":
                    entry.setCardColor("var(--bs-yellow)");
                    break;
            }
            totalDuration += entry.getEntryDuration();
        }

        for (Entries entry : entriesList) {
            float percentage = (float) entry.getEntryDuration() / totalDuration;
            int width = (int) (percentage * 100);
            //Mindestbreite für die Anzeige
            if (width < 13) {
                width = 13;
            }
            entry.setEntryWidth(width);
        }
        return entriesList;
    }


    public static List<DailyStats> getDailyStatsList(List<Days> MondayList, List<Days> TuesdayList, List<Days> WednesdayList, List<Days> ThursdayList, List<Days> FridayList, List<Days> SaturdayList) {
        List<DailyStats> dailyStatsList = new ArrayList<>();

        dailyStatsList.add(DailyStats.createDailyStats("Monday", MondayList));
        dailyStatsList.add(DailyStats.createDailyStats("Tuesday", TuesdayList));
        dailyStatsList.add(DailyStats.createDailyStats("Wednesday", WednesdayList));
        dailyStatsList.add(DailyStats.createDailyStats("Thursday", ThursdayList));
        dailyStatsList.add(DailyStats.createDailyStats("Friday", FridayList));
        dailyStatsList.add(DailyStats.createDailyStats("Saturday", SaturdayList));

        return dailyStatsList;
    }



    public static boolean isInCurrentWeek(LocalDate date, LocalDate aktuellesDatum) {
        // Überprüfe, ob das Datum in der gleichen Woche liegt
        return (!date.isBefore(aktuellesDatum.with(DayOfWeek.MONDAY)) && !date.isAfter(aktuellesDatum.with(DayOfWeek.SUNDAY)));
    }

    public static int calculateDuration(String startTime, String endTime) {
        int startHour = Integer.parseInt(startTime.substring(0, 2));
        int startMinute = Integer.parseInt(startTime.substring(3, 5));
        int endHour = Integer.parseInt(endTime.substring(0, 2));
        int endMinute = Integer.parseInt(endTime.substring(3, 5));

        int duration;

        return duration = (endHour * 60 + endMinute) - (startHour * 60 + startMinute);
    }

    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }


}
