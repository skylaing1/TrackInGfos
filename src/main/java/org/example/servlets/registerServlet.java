package org.example.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.login_service;
import org.example.database.register_service;
import org.example.entities.Mitarbeiter;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "registerServlet",value = "/register")
public class registerServlet extends HttpServlet {




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String passwordrepeat = request.getParameter("passwordrepeat");
        String personalNummer = request.getParameter("personalnummer");
        Date geburtsdatum = Date.valueOf(request.getParameter("geburtsdatum"));

        Mitarbeiter mitarbeiter = register_service.getMitarbeiterByPersonalNummer(Integer.parseInt(personalNummer));

        // Todo: Email ungültig prüfen: Prefix .com z.b./ Email bereits vergeben prüfen
        // Todo: Passwort ungültig prüfen: mindestens 8 Zeichen, mindestens 1 Großbuchstabe, mindestens 1 Zahl, mindestens 1 Sonderzeichen
        // Todo: Eingabe in die Datenbank in Kleinbuchstaben umwandeln / Außer Passwort
        if (mitarbeiter != null) {
            if (mitarbeiter.getName() != name) {
                if (password.length() >= 8 && !password.matches("[^!§$%&/()?=]+")) {
                    if (register_service.comparePassword(password, passwordrepeat)) {
                        if (email != null && email.contains("@")) {
                            register_service.registerUser(email, password, mitarbeiter);
                            response.sendRedirect("index.jsp");
                        } else {
                            //response.getWriter().println("Ungültige E-Mail-Adresse");
                            // Todo: Fehlermeldung: Ungültige E-Mail-Adresse
                        }
                        // Todo: Erfolgsmeldung
                    } else {
                        response.sendRedirect("register.jsp");
                        // Todo:  Fehlermeldung: Passwörter stimmen nicht überein
                    }

                } else {
                    // Todo:  Fehlermeldung: Passwort muss mindestens 8 Zeichen lang sein oder ein sonderzeichen beinhalten
                    response.getWriter().println("Passwort muss mindestens 8 Zeichen lang sein oder ein sonderzeichen beinhalten");
                }

            } else {
                    response.sendRedirect("register.jsp");
                    //Todo:  Fehlermeldung: Geburtsdatum stimmt nicht überein
                }


        } else {
            response.sendRedirect("register.jsp");
            // Todo:  Fehlermeldung: Mitarbeiter nicht gefunden
        }

    }

}
