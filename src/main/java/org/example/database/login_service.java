package org.example.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.example.entities.LoginData;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class login_service {

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
}
