package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Alert;
import org.example.MitarbeiterRefresh;
import org.example.ServletUtil;
import org.example.database.MitarbeiterTransaction;
import org.example.entities.Mitarbeiter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet(name = "managmentServlet", value = "/managment")
public class managmentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Mitarbeiter mymitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        int presentCounter = 0;
        int wasPresentTodayCounter = 0;

        if (mymitarbeiter == null || !mymitarbeiter.getAdmin()) {
            response.sendRedirect("/dashboard");
            return;
        }


        List<Mitarbeiter> mitarbeiterList = MitarbeiterTransaction.fetchAllMitarbeiterForTable();

        //Anpassen der Daten für die Anzeige
        for (Mitarbeiter mitarbeiter : mitarbeiterList) {
            mitarbeiter.setVorname(ServletUtil.capitalizeFirstLetter(mitarbeiter.getVorname()));
            mitarbeiter.setName(ServletUtil.capitalizeFirstLetter(mitarbeiter.getName()));

            String formattedWochenstunden = mitarbeiter.getWochenstunden() + " h";

            mitarbeiter.setWochenstundenFormatted(formattedWochenstunden);

            java.sql.Date geburtsdatum = Date.valueOf(mitarbeiter.getGeburtsdatum());
            LocalDate localDate_Geburtsdatum = geburtsdatum.toLocalDate();
            LocalDate  localDate_Einstellungsdatum = mitarbeiter.getEinstellungsdatum();

            //Formatiert das Datum in dd.MM.yyyy (z.B. 01.01.2000)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDate_EinstellungsDatum = localDate_Einstellungsdatum.format(formatter);
            String formattedDate_GeburtsDatum = localDate_Geburtsdatum.format(formatter);

            if (mitarbeiter.getWeekHoursProgress() != 0) {
                mitarbeiter.setWochenstundenProgressInPercent((int)(((double)mitarbeiter.getWeekHoursProgress() / mitarbeiter.getWochenstunden()) * 100));
            }

            mitarbeiter.setGeburtsdatumFormatted(formattedDate_GeburtsDatum);
            mitarbeiter.setEinstellungsdatumFormatted(formattedDate_EinstellungsDatum);
            switch (mitarbeiter.getPresent()) {
                case 1:
                    presentCounter++;
                    break;
                case 2:
                    wasPresentTodayCounter++;
                    break;
            }
        }

        List<Integer> usedPersonalNummer = mitarbeiterList.stream()
                .map(Mitarbeiter::getPersonalNummer)
                .toList();

        List<Integer> allPersonalNummer = IntStream.rangeClosed(1, 9999)
                .boxed()
                .collect(Collectors.toList());


        allPersonalNummer.removeAll(usedPersonalNummer);

        request.setAttribute("totalRows", mitarbeiterList.size());



        request.setAttribute("presentCounter", presentCounter);
        request.setAttribute("wasPresentTodayCounter", wasPresentTodayCounter);
        request.setAttribute("allAvailablePersonalNummer", allPersonalNummer);
        request.setAttribute("mitarbeiterList", mitarbeiterList);
        request.setAttribute("alert", request.getSession().getAttribute("alert"));
        session.removeAttribute("alert");

        request.getRequestDispatcher("WEB-INF/managment.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Mitarbeiter mymitarbeiter = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

        String vorname = request.getParameter("input_edit_vorname");
        int personalNummer = Integer.parseInt(request.getParameter("input_edit_personalnummer_hidden"));
        String nachname = request.getParameter("input_edit_nachname");
        String geburtsdatum = request.getParameter("input_edit_geburtsdatum");
        String eintrittsdatum = request.getParameter("input_edit_einstellungsdatum");
        String position = request.getParameter("input_edit_position");
        String onetimepassword = request.getParameter("input_edit_password");
        boolean admin = Boolean.parseBoolean(request.getParameter("input_edit_admin"));
        int wochenstunden = Integer.parseInt(request.getParameter("input_edit_wochenstunden"));

        String hashedPassword;
        Mitarbeiter editMitarbeiter = MitarbeiterTransaction.getMitarbeiterByPersonalNummer(personalNummer);

        if (!onetimepassword.isEmpty()) {
            hashedPassword = BCrypt.hashpw(onetimepassword, BCrypt.gensalt(12));
            MitarbeiterTransaction.deleteLoginDataAndTokens(personalNummer);
            editMitarbeiter.setOnetimepassword(hashedPassword);
            editMitarbeiter.setLoginData(null);

        }




        editMitarbeiter.setVorname(vorname);
        editMitarbeiter.setPersonalNummer(personalNummer);
        editMitarbeiter.setName(nachname);
        editMitarbeiter.setGeburtsdatum(LocalDate.parse(geburtsdatum));
        editMitarbeiter.setEinstellungsdatum(LocalDate.parse(eintrittsdatum));
        editMitarbeiter.setPosition(position);
        editMitarbeiter.setWochenstunden(wochenstunden);
        editMitarbeiter.setAdmin(admin);
        

        MitarbeiterTransaction.updateMitarbeiter(editMitarbeiter);

        Alert alert = Alert.successAlert( "Mitarbeiter erfolgreich bearbeitet!", "Der Mitarbeiter wurde erfolgreich bearbeitet.");
        if (editMitarbeiter.getPersonalNummer() == mymitarbeiter.getPersonalNummer()) {
            request.getSession().setAttribute("SessionMitarbeiter", editMitarbeiter);
        }
        request.getSession().setAttribute("alert", alert);
        response.sendRedirect("/managment");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String appPath = request.getServletContext().getRealPath("");
        MitarbeiterTransaction.deleteSingleMitarbeiter(id, appPath);
    }




}