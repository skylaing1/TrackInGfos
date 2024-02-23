package org.example.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Alert;
import org.example.database.MitarbeiterDAO;
import org.example.entities.Mitarbeiter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import jakarta.servlet.ServletException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "addMitarbeiterServlet", value = "/addMitarbeiter")
public class addMitarbeiterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        Mitarbeiter mitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        if (mitarbeiter == null || !mitarbeiter.getAdmin()) {
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


         String hashedPassword = BCrypt.hashpw(onetimepassword, BCrypt.gensalt(12));

        Mitarbeiter newMitarbeiter = new Mitarbeiter();
        newMitarbeiter.setVorname(vorname);
        newMitarbeiter.setPersonalNummer(personalNummer);
        newMitarbeiter.setName(nachname);
        newMitarbeiter.setGeburtsdatum(LocalDate.parse(geburtsdatum));
        newMitarbeiter.setEinstellungsdatum(LocalDate.parse(eintrittsdatum));
        newMitarbeiter.setPosition(position);
        newMitarbeiter.setOnetimepassword(hashedPassword);
        newMitarbeiter.setWochenstunden(wochenstunden);
        newMitarbeiter.setVerbleibendeUrlaubstage(28);
        newMitarbeiter.setAdmin(admin);
        newMitarbeiter.setProfilePicture("../resources/img/avatars/default.jpeg");

        MitarbeiterDAO.addMitarbeiter(newMitarbeiter);

        Alert alert = Alert.successAlert("Mitarbeiter erfolgreich hinzugefügt", "Der Mitarbeiter wurde erfolgreich hinzugefügt.");
        session.setAttribute("alert", alert);
        response.sendRedirect("/managment");
    }
}