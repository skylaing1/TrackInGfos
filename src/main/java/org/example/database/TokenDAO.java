package org.example.database;

import org.example.entities.LoginData;
import org.example.entities.Token;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TokenDAO {

    public static void storeTokenInDatabase(String token_content, LoginData loginData) {
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

    public static void deleteOldTokens() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            LocalDateTime localDateTime = LocalDateTime.now().minusDays(14);
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

            session.createQuery("DELETE FROM Token WHERE token_timestamp < :date")
                    .setParameter("date", date)
                    .executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            // Handle Exceptions
            e.printStackTrace();
        }

    }

    public static Token getValidToken(String tokenContent) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory();
             Session session = factory.openSession()) {

            session.beginTransaction();

            List<Token> tokens = session.createQuery("FROM Token WHERE token_content = :token_content", Token.class)
                    .setParameter("token_content", tokenContent)
                    .list();

            session.getTransaction().commit();

            if (tokens != null && !tokens.isEmpty()) {
                LocalDateTime localDateTime = LocalDateTime.now().minusDays(14);
                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                for (Token token : tokens) {
                    if (token.getTimestamp().before(date)) {
                        deleteTokenByContent(tokenContent);
                        System.out.println("TokenDAO.getValidToken: Token ist abgelaufen");
                    } else {
                        return token;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            // Handle Exceptions
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}