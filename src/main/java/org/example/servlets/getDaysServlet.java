package org.example.servlets;


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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "getDaysServlet", value = "/getDays")
public class getDaysServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int personalNummer = Integer.parseInt(request.getParameter("id"));
        List<Days> dayslist = DaysDAO.fetchDaysByMitarbeiter(personalNummer);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .create();
        String daysJson = gson.toJson(dayslist);

        // Content typ JSON
        response.setContentType("application/json");
        // Response mit JSON String
        response.getWriter().write(daysJson);

    }


}