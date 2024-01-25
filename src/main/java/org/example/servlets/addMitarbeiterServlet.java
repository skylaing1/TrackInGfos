package org.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.MitarbeiterDAO;
import org.example.entities.Mitarbeiter;
import java.io.IOException;
import java.util.Date;

import jakarta.servlet.ServletException;

@WebServlet(name = "addMitarbeiterServlet", value = "/addMitarbeiter")
public class addMitarbeiterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        if (mitarbeiter == null || !mitarbeiter.isAdmin()) {
            response.sendRedirect("/dashboard");
            return;
        }

        String vorname = request.getParameter("input_vorname");
        int personalNummer = Integer.parseInt(request.getParameter("input_personalnummer"));
        String nachname = request.getParameter("input_nachname");
        String geburtsdatum = request.getParameter("input_geburtsdatum");
        String eintrittsdatum = request.getParameter("input_einstellungsdatum");
        String position = request.getParameter("input_position");
        String onetimepassword = request.getParameter("input_password");
        boolean admin = Boolean.parseBoolean(request.getParameter("input_admin"));
        int wochenstunden = Integer.parseInt(request.getParameter("input_wochenstunden"));

        System.out.println("Vorname: " + vorname);
        System.out.println("Personalnummer: " + personalNummer);
        System.out.println("Name: " + nachname);
        System.out.println("Geburtsdatum: " + geburtsdatum);
        System.out.println("Eintrittsdatum: " + eintrittsdatum);
        System.out.println("Position: " + position);
        System.out.println("Onetimepassword: " + onetimepassword);
        System.out.println("Wochenstunden: " + wochenstunden);
        System.out.println("Admin: " + admin);
        //TODO: mach Personalnummer ein Select und keine zahl eingabe in HTML
        //TODO: Einmal Passwort verschlüsseln vllt auch Generieren in der Erfolgsmeldung
        //TODO: Erfolgreich hinzugefügt Meldung

        MitarbeiterDAO.addMitarbeiter(vorname, personalNummer, nachname, geburtsdatum, eintrittsdatum, position, onetimepassword, wochenstunden, admin);

        response.sendRedirect("/managment");
    }
}
//TODO: https://en.m.wikipedia.org/wiki/Post/Redirect/Get