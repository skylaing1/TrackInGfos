package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.MitarbeiterDAO;
import org.example.entities.Mitarbeiter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@WebServlet(name = "managmentServlet", value = "/managment")
public class managmentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        if (mitarbeiter == null || !mitarbeiter.getAdmin()) {
            response.sendRedirect("/dashboard");
            return;
        }

        List<Mitarbeiter> mitarbeiterList = MitarbeiterDAO.fetchAllMitarbeiterForTable();

        List<Integer> usedPersonalNummer = mitarbeiterList.stream()
                .map(Mitarbeiter::getPersonalNummer)
                .collect(Collectors.toList());

        List<Integer> allPersonalNummer = IntStream.rangeClosed(1, 9999)
                .boxed()
                .collect(Collectors.toList());


        allPersonalNummer.removeAll(usedPersonalNummer);


        int totalRows = mitarbeiterList.size();


        request.setAttribute("totalRows", totalRows);

        String pageParam = request.getParameter("page");

        int currentPage = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
        int rowsPerPage = 30;


        int begin = (currentPage - 1) * rowsPerPage;
        int end = Math.min(begin + rowsPerPage, mitarbeiterList.size());

        List<Mitarbeiter> sublist = mitarbeiterList.subList(begin, end);
        System.out.println("Test");

        request.setAttribute("allAvailablePersonalNummer", allPersonalNummer);
        request.setAttribute("mitarbeiterList", sublist);

        request.getRequestDispatcher("WEB-INF/managment.jsp").forward(request, response);
    }


}