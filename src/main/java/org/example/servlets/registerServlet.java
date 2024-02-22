package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Alert;
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

        if (mitarbeiter != null) {
            if (mitarbeiter.getLoginData() == null) {
                if (BCrypt.checkpw(oneTimePassword, mitarbeiter.getOnetimepassword())) {
                    if (password.equals(password_repeat)) {

                        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
                        LoginDataDAO.registerLoginData(email, hashedPassword, mitarbeiter);

                        Alert alert = Alert.successAlert("Registrierung erfolgreich", "Die Registrierung war erfolgreich. Sie können sich nun mit Ihren Anmeldedaten einloggen.");
                        request.setAttribute("alert", alert);
                        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
                    } else {
                        Alert alert = Alert.dangerAlert("Passwort und wiederholtes Passwort stimmen nicht überein", "Die eingegebenen Passwörter stimmen nicht überein. Bitte überprüfe, ob das eingegebene Passwort mit dem wiederholten Passwort übereinstimmt und versuche es erneut.");
                        request.setAttribute("alert", alert);
                        request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
                    }
                } else {
                    //One-Time-Password falsch
                    Alert alert = Alert.dangerAlert("One-Time-Password falsch", "Das eingegebene One-Time-Password ist falsch. Bitte überprüfe das eingegebene One-Time-Password und versuche es erneut.");
                    request.setAttribute("alert", alert);
                    request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
                }
            } else {
                //Mitarbeiter hat bereits einen Account
                Alert alert = Alert.dangerAlert("Mitarbeiter bereits vorhanden", "Es wurde versucht, einen neuen Account für den Mitarbeiter mit der Personalnummer " + personalNummer + " anzulegen. Dieser Mitarbeiter hat bereits einen bestehenden Account. Bitte überprüfe die eingegebene Personalnummer.");
                request.setAttribute("alert", alert);
                request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
            }
        } else {
            //Mitarbeiter nicht gefunden
            Alert alert = Alert.dangerAlert("Mitarbeiter nicht gefunden", "Der Mitarbeiter mit der Personalnummer " + personalNummer + " wurde nicht gefunden. " + "Bitte überprüfe die eingegebene Personalnummer und versuche es erneut.");
            request.setAttribute("alert", alert);
            request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
        }
    }
}
