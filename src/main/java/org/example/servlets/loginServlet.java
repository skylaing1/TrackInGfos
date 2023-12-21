package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.login_service;

import java.io.IOException;

@WebServlet(name = "loginServlet",value = "/login")
public class loginServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {


        // Benutzername und Passwort aus dem Formular erhalten
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Authentifizierung mit Hilfe der LoginService-Klasse
       if (login_service.authenticateUser(email, password)) {
           response.sendRedirect("dashboard.jsp");
       } else {
           response.sendRedirect("register.jsp");
       }



    }
}