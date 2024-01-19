package org.example;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.TokenDAO;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.example.entities.Token;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.UUID;


public class ServletUtil {


    public static boolean checkSessionAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        if (session == null || session.getAttribute("SessionMitarbeiter") == null) {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("rememberMe")) {
                        Token token = TokenDAO.getValidToken(cookie.getValue());
                        if (token != null) {
                            session = request.getSession(true);
                            session.setAttribute("SessionMitarbeiter", token.getLoginData().getMitarbeiter());
                            response.sendRedirect("/dashboard");
                            System.out.println("Token funktionier"); //Test
                            return true;
                        }
                        cookie.setMaxAge(0);
                        return false;
                    }
                }
            }

            return false;
        }
        response.sendRedirect("/dashboard");
        return true;
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


}
