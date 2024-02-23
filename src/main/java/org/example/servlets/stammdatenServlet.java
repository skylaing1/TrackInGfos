package org.example.servlets;

import com.mysql.cj.log.Log;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Alert;
import org.example.database.LoginDataDAO;
import org.example.database.TokenDAO;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "stammdatenServlet", value = "/stammdaten")
public class stammdatenServlet extends HttpServlet {

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false);
            Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");
            Alert alert = null;

            String email = request.getParameter("email");
            String OldPassword = request.getParameter("input_password_old");
            String NewPassword = request.getParameter("input_password_new");

            if (BCrypt.checkpw(OldPassword, mitarbeiter.getLoginData().getPasswort())) {
                LoginData loginData = mitarbeiter.getLoginData();
                loginData.setEmail(email);
                if (!NewPassword.isEmpty()) {
                    loginData.setPasswort(BCrypt.hashpw(NewPassword, BCrypt.gensalt()));
                }
                LoginDataDAO.updateLoginData(loginData);
                 alert = Alert.successAlert("Stammdaten erfolgreich geändert", "Ihre Stammdaten wurden erfolgreich geändert.");
                TokenDAO.deleteToken(mitarbeiter);
            } else {
                 alert = Alert.dangerAlert("Stammdaten nicht geändert", "Ihr Aktuelles Passwort ist nicht korrekt.");
            }

            session.setAttribute("alert", alert);
            response.sendRedirect("/profile");
        }
}