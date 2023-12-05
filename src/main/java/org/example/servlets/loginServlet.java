package org.example.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;


import java.io.IOException;

@WebServlet(name = "login",value = "/login")
public class loginServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        String benutzername = request.getParameter("benutzername");
        String passwort = request.getParameter("passwort");

         /*Überprüfe die Anmeldeinformationen in der Datenbank (verwende Hibernate)
        boolean authentifiziert = Hier Hibernate verwenden, um die Anmeldeinformationen zu überprüfen */

        if ("testuser".equals(benutzername) && "1".equals(passwort)) {
            // Weiterleitung zur erfolgreichen Anmeldung
            response.sendRedirect("dashboard.jsp");
        } else {
            // Fehlermeldung oder Weiterleitung zur Login-Seite
            response.sendRedirect("register.jsp");
        }
    }
}
