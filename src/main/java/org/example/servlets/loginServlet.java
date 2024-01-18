package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.LoginDataDAO;
import org.example.database.TokenDAO;
import org.example.ServletUtil;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import jakarta.servlet.http.Cookie;


import java.io.IOException;

import static org.example.ServletUtil.generateSecureToken;

@WebServlet(name = "loginServlet",value = "/login")
public class loginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       if (ServletUtil.checkSessionAndRedirect(request, response)) {
           System.out.println("RememberMe cookie angemeldetokkkk"); //Test
           return;
       }
        System.out.println("Keine Session"); //Test
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
        LoginData loginData = LoginDataDAO.getLoginDataByEmail(email);

        if (loginData == null) {
                System.out.println("kein Account gefunden");
                response.sendRedirect("/login");
                return;
        }

        Mitarbeiter mitarbeiter = loginData.getMitarbeiter();


       if (ServletUtil.authenticateUser(email, password,  loginData)) {
           HttpSession session = request.getSession();
           session.setAttribute("SessionMitarbeiter", mitarbeiter);


           if (rememberMe) {

               String token_content = generateSecureToken();
               TokenDAO.storeTokenInDatabase(token_content, loginData);
               Cookie rememberMeCookie = new Cookie("rememberMe", token_content);
               rememberMeCookie.setMaxAge(60 * 60 * 24 * 14); // 14 Tage
               rememberMeCookie.setHttpOnly(true);
               response.addCookie(rememberMeCookie);
           }

           response.sendRedirect("/dashboard");

           System.out.println("Login erfolgreich");
           //Todo: Erfolgsmeldung

       } else {
           System.out.println("Login erfolgreich nicht");
           request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
           //Todo: Fehlermeldung: Login fehlgeschlagen
       }



    }
}