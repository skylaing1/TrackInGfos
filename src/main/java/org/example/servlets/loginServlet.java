package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Alert;
import org.example.UpdateMessage;
import org.example.database.LoginDataTransaction;
import org.example.database.TokenTransaction;
import org.example.ServletUtil;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import jakarta.servlet.http.Cookie;


import java.io.IOException;
import java.util.UUID;


@WebServlet(name = "loginServlet",value = "/login")
public class loginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Erhalten der Anmeldeinformationen
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));

        LoginData loginData = LoginDataTransaction.getLoginDataByEmail(email);

        // Wenn Email nicht in der Datenbank gefunden wurde
        if (loginData == null) {
            Alert alert = Alert.dangerAlert("Login Fehlgeschlagen", "Die Anmeldung konnte nicht abgeschlossen werden. Bitte überprüfen Sie Ihre Anmeldeinformationen und versuchen Sie es erneut.");
            request.setAttribute("alert", alert);
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
                return;
        }

        Mitarbeiter mitarbeiter = loginData.getMitarbeiter();

       if (ServletUtil.authenticateUser(email, password,  loginData)) {
           HttpSession session = request.getSession();
           session.setAttribute("SessionMitarbeiter", mitarbeiter);
           UpdateMessage.RefreshMessage();


           if (rememberMe) {
               String token_content = UUID.randomUUID().toString();
               TokenTransaction.storeTokenInDatabase(token_content, loginData);
               Cookie rememberMeCookie = new Cookie("rememberMe", token_content);
               rememberMeCookie.setMaxAge(60 * 60 * 24 * 14); // 14 Tage
               rememberMeCookie.setHttpOnly(true);
               response.addCookie(rememberMeCookie);
           }

           response.sendRedirect("/dashboard");
       } else {
           Alert alert = Alert.dangerAlert("Login Fehlgeschlagen", "Die Anmeldung konnte nicht abgeschlossen werden. Bitte überprüfen Sie Ihre Anmeldeinformationen und versuchen Sie es erneut.");
           request.setAttribute("alert", alert);
           request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
       }
    }
}