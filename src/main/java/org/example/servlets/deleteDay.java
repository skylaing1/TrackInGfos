package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Alert;
import org.example.database.DaysDAO;


import java.io.IOException;

@WebServlet(name = "deleteDay", value = "/deleteDay")
public class deleteDay extends HttpServlet {
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int daysId = Integer.parseInt(request.getParameter("id"));
        Alert alert = DaysDAO.deleteDayAndEntries(daysId);
        HttpSession session = request.getSession(false);
        session.setAttribute("alert", alert);
    }
}