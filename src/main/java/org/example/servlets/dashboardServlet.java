package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.ServletUtil;
import org.example.entities.Entries;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "dashboardServlet", value = "/dashboard")
public class dashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Entries> entriesList = ServletUtil.getCurrentEntriesForDashboard(request);



        request.setAttribute("entries", entriesList);



        request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
    }

}