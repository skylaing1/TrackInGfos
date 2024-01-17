package org.example;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.TokenDAO;
import org.example.entities.LoginData;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.UUID;

import static org.example.database.TokenDAO.deleteOldTokens;

public class ServletUtil {


    public static boolean checkSessionAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        if (session == null || session.getAttribute("SessionMitarbeiter") == null) {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("rememberMe")) {
                        deleteOldTokens();
                        if (TokenDAO.checkToken(cookie.getValue())) {
                            session = request.getSession();
                            session.setAttribute("SessionMitarbeiter", TokenDAO.getMitarbeiterByToken(cookie.getValue()));
                            response.sendRedirect("/dashboard");
                            return true;
                        }
                    }
                }
            }
            response.sendRedirect("/dashboard");
            return true;
        }
        return false;
    }


    public static boolean authenticateUser(String email, String password, LoginData loginData) {

            if (loginData != null) {
                return BCrypt.checkpw(password, loginData.getPasswort());
            } else {
                return false; // Benutzer mit der gegebenen E-Mail-Adresse nicht gefunden
            }
    }

    public static String generateSecureToken() {
        return UUID.randomUUID().toString();
    }


    public static boolean comparePassword(String password, String password2) {
    return password.equals(password2);
}

    public static boolean hasRememberMeCookie(HttpServletRequest request) { //Testen ob der RememberMe Cookie gesetzt ist
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("rememberMe")) {
                    deleteOldTokens();
                    if (TokenDAO.checkToken(cookie.getValue())) {
                        return true;
                    } else {
                        cookie.setMaxAge(0);
                        return false;
                    }

                }
            }
        }
        return false;
    }




}
