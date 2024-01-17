package org.example.database;

import org.example.entities.LoginData;
import org.example.entities.Token;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class TokenDAO {

    public static void storeTokenInDatabase ( String token_content, LoginData loginData) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {
            System.out.println("TokenDAO.storeTokenInDatabase");

            session.beginTransaction();

            Token token = new Token();
            token.setToken_content(token_content);
            token.setLoginData(loginData);

            token.setToken_timestamp();

            session.persist(token);

            session.getTransaction().commit();



        } catch (Exception e) {
            // Handle Exceptions
            e.printStackTrace();
        }
    }

    public static Token getMitarbeiterByToken(String value) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            Token token = (Token) session.createQuery("FROM Token WHERE token_content = :token_content")
                    .setParameter("token_content", value)
                    .uniqueResult();

            session.getTransaction().commit();

            return token;
        } catch (Exception e) {
            // Handle Exceptions
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteTokenByContent(String tokenContent) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();


            Token token = (Token) session.createQuery("FROM Token WHERE token_content = :token_content")
                    .setParameter("token_content", tokenContent)
                    .uniqueResult();

            if (token != null) {

                session.remove(token);
            }


            session.getTransaction().commit();

        } catch (Exception e) {
            // Handle Exceptions
            e.printStackTrace();
        }
    }

    public static void deleteTokensOlderThan14Days() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();


            session.createQuery("DELETE FROM Token WHERE token_timestamp < :date")
                    .setParameter("date", LocalDate.now().minusDays(14))
                    .executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            // Handle Exceptions
            e.printStackTrace();
        }

    }

    public static boolean checkToken(String tokenContent) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            Token token = (Token) session.createQuery("FROM Token WHERE token_content = :token_content")
                    .setParameter("token_content", tokenContent)
                    .uniqueResult();

            session.getTransaction().commit();

            if (token != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // Handle Exceptions
            e.printStackTrace();
            return false;
        }

    }
}
