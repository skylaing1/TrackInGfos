package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "updateMitarbeiterServlet", value = "/updateMitarbeiter")
public class updateMitarbeiterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            String vorname = request.getParameter("input_vorname");
            int personalNummer = Integer.parseInt(request.getParameter("input_personalnummer"));
            String nachname = request.getParameter("input_nachname");
            String geburtsdatum = request.getParameter("input_geburtsdatum");
            String eintrittsdatum = request.getParameter("input_einstellungsdatum");
            String position = request.getParameter("input_position");
            String onetimepassword = request.getParameter("input_password");
            boolean admin = Boolean.parseBoolean(request.getParameter("input_admin"));
            int wochenstunden = Integer.parseInt(request.getParameter("input_wochenstunden"));


    }
}