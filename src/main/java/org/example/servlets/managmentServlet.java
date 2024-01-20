package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.MitarbeiterDAO;
import org.example.entities.Mitarbeiter;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "managmentServlet", value = "/managment")
public class managmentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Mitarbeiter> mitarbeiterList = MitarbeiterDAO.fetchAllMitarbeiterForTable();
        int totalRows = mitarbeiterList.size();

        request.setAttribute("totalRows", totalRows);

        String pageParam = request.getParameter("page");

        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int rowsPerPage = 30;


        int begin = (currentPage - 1) * rowsPerPage;
        int end = Math.min(begin + rowsPerPage, mitarbeiterList.size());

        List<Mitarbeiter> sublist = mitarbeiterList.subList(begin, end);


        request.setAttribute("mitarbeiterList", sublist);


        request.getRequestDispatcher("WEB-INF/managment.jsp").forward(request, response);
    }
}