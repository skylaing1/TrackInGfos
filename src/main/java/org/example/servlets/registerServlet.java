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

@WebServlet(name = "registerServlet",value = "/register")
public class registerServlet extends HttpServlet {




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {



        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String personalNummer = request.getParameter("personalnummer");




        Mitarbeiter mitarbeiter = register_service.getMitarbeiterByPersonalNummer(Integer.parseInt(personalNummer));

        // Überprüfen Sie, ob der Mitarbeiter gefunden wurde, bevor Sie versuchen, ihn zu registrieren
        if (mitarbeiter != null) {
            register_service.registerUser(email, password, mitarbeiter);
            // Hier können Sie weitere Aktionen durchführen, z.B. eine Erfolgsmeldung an den Benutzer senden
        } else {
            // Hier können Sie eine Fehlermeldung senden, wenn der Mitarbeiter nicht gefunden wurde
        }

    }

}
