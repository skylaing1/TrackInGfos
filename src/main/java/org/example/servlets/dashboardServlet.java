package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.ServletUtil;
import org.example.database.DaysDAO;
import org.example.database.EntriesDAO;
import org.example.database.UtilDAO;
import org.example.entities.Days;
import org.example.entities.Entries;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.example.Alert;
import org.example.entities.Mitarbeiter;

@WebServlet(name = "dashboardServlet", value = "/dashboard")
public class dashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        int geleisteteStundenInProzent = 0;
        int stundenKontingent = mitarbeiter.getWochenstunden();
        int urlaubAnspruch = 28;
        int geleisteteStunden = 0;


        List<Entries> entriesList = ServletUtil.getCurrentEntriesForDashboard(request);
        List<Days> daysList = DaysDAO.getDaysofCurrentYear(request);
        List<Days> daysListCurrentMonth = ServletUtil.getDaysofCurrentMonth(request, daysList);

        for (Days day : daysList) {


            if (day.getStatus().equals("Urlaub")) {
                urlaubAnspruch--;
            }
        }

        for (Days day : daysListCurrentMonth) {
            if (day.getStatus().equals("Anwesend") || day.getStatus().equals("Dienstreise")) {
                geleisteteStunden += day.getPresentDuration();
            }
        }

        if (geleisteteStunden > 0) {
            geleisteteStundenInProzent = (geleisteteStunden * 100) / (stundenKontingent * 60);
        }




        Alert alert = (Alert) session.getAttribute("alert");

        request.setAttribute("alert", alert);
        session.removeAttribute("alert");

        request.setAttribute("entries", entriesList);

        request.setAttribute("geleisteteStundenInProzent", geleisteteStundenInProzent);
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

