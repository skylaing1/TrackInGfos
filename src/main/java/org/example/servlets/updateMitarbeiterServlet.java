package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.MitarbeiterDAO;
import org.example.entities.Mitarbeiter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;


@WebServlet(name = "updateMitarbeiterServlet", value = "/updateMitarbeiter")
public class updateMitarbeiterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

          String vorname = request.getParameter("input_edit_vorname");
            int personalNummer = Integer.parseInt(request.getParameter("input_edit_personalnummer_hidden"));
            String nachname = request.getParameter("input_edit_nachname");
            String geburtsdatum = request.getParameter("input_edit_geburtsdatum");
            String eintrittsdatum = request.getParameter("input_edit_einstellungsdatum");
            String position = request.getParameter("input_edit_position");
            String onetimepassword = request.getParameter("input_edit_password");
            boolean admin = Boolean.parseBoolean(request.getParameter("input_edit_admin"));
            int wochenstunden = Integer.parseInt(request.getParameter("input_edit_wochenstunden"));

             String hashedPassword = null;

        if (onetimepassword != null) {
                hashedPassword = BCrypt.hashpw(onetimepassword, BCrypt.gensalt(12));
                MitarbeiterDAO.deleteLoginDataAndTokens(personalNummer);
            }
                
            
           
           

             MitarbeiterDAO.updateMitarbeiter(vorname, personalNummer, nachname, geburtsdatum, eintrittsdatum, position, hashedPassword, wochenstunden, admin);

              response.sendRedirect("/managment");
    }
}