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
        String startDate = request.getParameter("input_datum_von");
        String endDate = request.getParameter("input_datum_bis");

        HttpSession session1 = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session1.getAttribute("SessionMitarbeiter");

        int daysId;

        if (daysIdStr == null || daysIdStr.isEmpty()) {
            daysId = 0;
        } else {
            daysId = Integer.parseInt(daysIdStr);
        }

        System.out.println("id: " + daysId);
        System.out.println("status: " + status);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate: " + endDate);

        DaysDAO.saveOrUpdateDays(daysId, status, startDate, endDate, mitarbeiter);




        response.sendRedirect("/calendar");

    }




}
