package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ServletUtil;
import org.example.database.LoginDataDAO;
import org.example.database.MitarbeiterDAO;
import org.example.entities.Mitarbeiter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet(name = "registerServlet", value = "/register")
public class registerServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ServletUtil.checkSessionAndRedirect(request, response)) {
            System.out.println("RememberMe cookie angemeldet"); //Test
            return;
        }
        System.out.println("Session war noch da"); //Test
        request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password_repeat = request.getParameter("password_repeat");
        String personalNummer = request.getParameter("personalnummer");
        String oneTimePassword = request.getParameter("oneTimePassword");

        Mitarbeiter mitarbeiter = MitarbeiterDAO.getMitarbeiterByPersonalNummer(Integer.parseInt(personalNummer));


        // Todo: Email ungültig prüfen: Prefix .com z.b./ Email bereits vergeben prüfen

        if (mitarbeiter != null) {
            if (mitarbeiter.getLoginData() == null) {
                if (BCrypt.checkpw(oneTimePassword, mitarbeiter.getOnetimepassword())) {

                    if (ServletUtil.comparePassword(password, password_repeat)) {

                        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

                        LoginDataDAO.registerLoginData(email, hashedPassword, mitarbeiter);
                        response.sendRedirect("/login");

                        // Todo: Erfolgsmeldung
                    } else {

                        response.sendRedirect("/register");
                        // Todo: Fehlermeldung: Passwörter stimmen nicht überein
                    }
                } else {

                    response.sendRedirect("/login");
                    //Todo: Fehlermeldung: One-Time-Password falsch
                }
            } else {
                    //TODO: Fehlermeldung: Mitarbeiter hat bereits einen Account
                    response.sendRedirect("/login");
            }

            } else {
                response.sendRedirect("404.jsp");
                // Todo: Fehlermeldung: Mitarbeiter nicht gefunden / Personalnummer falsch
            }


        }

        //Todo: Nur ein Account pro Mitarbeiter

    }
