package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.LoginDataDAO;
import org.example.database.login_service;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;

import java.io.IOException;

@WebServlet(name = "loginServlet",value = "/login")
public class loginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("SessionMitarbeiter") != null) {

            response.sendRedirect("/dashboard");
            return;
        }

        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        String email = request.getParameter("email");
        String password = request.getParameter("password");
        LoginData loginData = LoginDataDAO.getLoginDataByEmail(email);
        Mitarbeiter mitarbeiter = loginData.getMitarbeiter();

        //Todo: Angemeldet bleiben hinzufügen / Cookies Implementieren / Sessions Implementieren

       if (login_service.authenticateUser(email, password, loginData)) {


           HttpSession session = request.getSession();
           session.setAttribute("SessionMitarbeiter", mitarbeiter);
           response.sendRedirect("/dashboard");

           System.out.println("Login erfolgreich");
           //Todo: Erfolgsmeldung

       } else {
           System.out.println("Login erfolgreich nicht");
           response.sendRedirect("login.jsp");
           //Todo: Fehlermeldung: Login fehlgeschlagen
       }



    }
}