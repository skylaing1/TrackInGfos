package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.database.login_service;

import java.io.IOException;

@WebServlet(name = "loginServlet",value = "/login")
public class loginServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        HttpSession session = request.getSession();
        System.out.println(session.getId());
        String sessionID = session.getId();
        Cookie sessionCookie = new Cookie("JSESSIONID", sessionID);
        response.addCookie(sessionCookie);
        System.out.println(sessionCookie.getValue());



        String email = request.getParameter("email");
        String password = request.getParameter("password");


        //Todo: Angemeldet bleiben hinzufügen / Cookies Implementieren / Sessions Implementieren

       if (login_service.authenticateUser(email, password)) {
           response.sendRedirect("dashboard.jsp");
           //Todo: Erfolgsmeldung
       } else {
           response.sendRedirect("index.jsp");
           //Todo: Fehlermeldung: Login fehlgeschlagen
       }



    }
}