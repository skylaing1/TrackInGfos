package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.LoginDataDAO;
import org.example.database.TokenDAO;
import org.example.database.login_service;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import jakarta.servlet.http.Cookie;

import java.io.IOException;

import static org.example.database.login_service.generateSecureToken;

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
        Boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
        LoginData loginData = LoginDataDAO.getLoginDataByEmail(email);
        Mitarbeiter mitarbeiter = loginData.getMitarbeiter();

        //Todo: Angemeldet bleiben hinzufügen / Cookies Implementieren / Sessions Implementieren

       if (login_service.authenticateUser(email, password,  loginData)) {

           if (rememberMe) {
               String token_content = generateSecureToken();
               TokenDAO.storeTokenInDatabase(email, token_content);
               Cookie rememberMeCookie = new Cookie("rememberMe", token_content);
               rememberMeCookie.setMaxAge(60 * 60 * 24 * 14); // 14 Tage
               rememberMeCookie.setHttpOnly(true);
               response.addCookie(rememberMeCookie);
           }


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