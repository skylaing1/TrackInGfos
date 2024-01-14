package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.login_service;

import java.io.IOException;

@WebServlet(name = "loginServlet",value = "/login")
public class loginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        String email = request.getParameter("email");
        String password = request.getParameter("password");


        //Todo: Angemeldet bleiben hinzufügen / Cookies Implementieren / Sessions Implementieren

       if (login_service.authenticateUser(email, password)) {


           HttpSession session = request.getSession();
           session.setAttribute("SessionUsername", );

           //Todo: Erfolgsmeldung
       } else {
           response.sendRedirect("index.jsp");
           //Todo: Fehlermeldung: Login fehlgeschlagen
       }



    }
}