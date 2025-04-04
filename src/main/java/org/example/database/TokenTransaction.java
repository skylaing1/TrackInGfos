package org.example.database;

import org.example.ServerService;
import org.example.entities.LoginData;
import org.example.entities.Mitarbeiter;
import org.example.entities.Token;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TokenTransaction {

    public static void storeTokenInDatabase(String token_content, LoginData loginData) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                Token token = new Token();
                token.setToken_content(token_content);
                token.setLoginData(loginData);
                token.setToken_timestamp();

                session.persist(token);

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


    public static void deleteTokenByContent(String tokenContent) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                Query<Token> query = session.createQuery("FROM Token WHERE token_content = :tokenContent", Token.class);
                query.setParameter("tokenContent", tokenContent);
                Token token = query.uniqueResult();

                if (token != null) {
                    session.remove(token);
                }
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

    public static Token getValidToken(String tokenContent) {
        try (Session session = ServerService.getSessionFactory().openSession()) {

            List<Token> tokens = session.createQuery("FROM Token WHERE token_content = :token_content", Token.class)
                    .setParameter("token_content", tokenContent)
                    .list();


            if (tokens != null && !tokens.isEmpty()) {
                LocalDateTime localDateTime = LocalDateTime.now().minusDays(14);
                Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

                for (Token token : tokens) {
                    if (token.getTimestamp().before(date)) {
                        deleteTokenByContent(tokenContent);
                    } else {
                        return token;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void deleteToken(Mitarbeiter mitarbeiter) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                Query<Token> query = session.createNativeQuery("DELETE FROM Token WHERE credentials_id = :loginData", Token.class);
                query.setParameter("loginData", mitarbeiter.getLoginData().getCredentialsId());
                query.executeUpdate();

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