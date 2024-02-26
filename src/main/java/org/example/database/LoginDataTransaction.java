package org.example.database;

import org.example.ServerService;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.entities.LoginData;
import org.hibernate.query.Query;

public class LoginDataTransaction {

    public static LoginData getLoginDataByEmail(String email) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Query<LoginData> query = session.createQuery("FROM LoginData WHERE email = :email", LoginData.class);
            return query.setParameter("email", email.toLowerCase()).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void registerLoginData(String email, String hashedPassword, Mitarbeiter mitarbeiter) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                LoginData loginData = new LoginData();
                loginData.setEmail(email.toLowerCase());
                loginData.setPasswort(hashedPassword);
                loginData.setMitarbeiter(mitarbeiter);

                session.persist(loginData);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateLoginData(LoginData loginData) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                session.merge(loginData);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
