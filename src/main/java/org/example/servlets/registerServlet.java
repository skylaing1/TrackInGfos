package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.LoginDataDAO;
import org.example.database.MitarbeiterDAO;
import org.example.database.login_service;
import org.example.database.register_service;
import org.example.entities.Mitarbeiter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "registerServlet",value = "/register")
public class registerServlet extends HttpServlet {




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password_repeat = request.getParameter("password_repeat");
        String personalNummer = request.getParameter("personalnummer");
        String oneTimePassword = request.getParameter("oneTimePassword");

        Mitarbeiter mitarbeiter = MitarbeiterDAO.getMitarbeiterByPersonalNummer(Integer.parseInt(personalNummer));

        // Todo: Email ungültig prüfen: Prefix .com z.b./ Email bereits vergeben prüfen
        // Todo: Passwort ungültig prüfen: mindestens 8 Zeichen, mindestens 1 Großbuchstabe, mindestens 1 Zahl, mindestens 1 Sonderzeichen
        // Todo: Eingabe in die Datenbank in Kleinbuchstaben umwandeln / Außer Passwort
        if (mitarbeiter != null) {

            if (mitarbeiter.getOnetimepassword().equals(oneTimePassword)) {

                if (register_service.comparePassword(password, password_repeat)) {

                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                    LoginDataDAO.registerLoginData(email, hashedPassword, mitarbeiter);
                    response.sendRedirect("index.jsp");

                    // Todo: Erfolgsmeldung
                } else {

                    response.sendRedirect("register.jsp");
                    // Todo: Fehlermeldung: Passwörter stimmen nicht überein
                }
            } else {

                response.sendRedirect("index.jsp");
                //Todo: Fehlermeldung: One-Time-Password falsch
            }

        } else {
            response.sendRedirect("404.jsp");
            // Todo: Fehlermeldung: Mitarbeiter nicht gefunden / Personalnummer falsch
        }


    }

    //Todo: Nur ein Account pro Mitarbeiter

}
