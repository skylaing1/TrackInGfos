package org.example.database;


import org.mindrot.jbcrypt.BCrypt;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class register_service {

    public static boolean comparePassword(String password, String password2) {
        return password.equals(password2);
    }
}