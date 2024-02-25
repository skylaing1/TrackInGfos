package org.example.database;

import org.example.ServerService;
import org.example.entities.LoginData;
import org.example.entities.Message;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageDAO {
    public static void deleteAllExpiredMessages() {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                String sql = "DELETE FROM Message WHERE datum < :expirationDate";
                Query query = session.createNativeQuery(sql);
                query.setParameter("expirationDate", LocalDateTime.now().minusDays(6));
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




    public static void saveAllMessagesToAdmins(List<Message> adminMessages, List<Message> messages) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                List<Mitarbeiter> admins = session.createQuery("from Mitarbeiter where admin = true", Mitarbeiter.class).list();


                // Iterator Gegen ConcurrentModificationException
                List<Message> finalAdminMessages = new ArrayList<>();
                Iterator<Message> adminIterator = adminMessages.iterator();

                while (adminIterator.hasNext()) {
                    Message adminMessage = adminIterator.next();
                    boolean matchesDatabaseMessage = false;

                    for (Message databaseMessage : messages) {
                        if (adminMessage.getMessage().equals(databaseMessage.getMessage()) && adminMessage.getDatum().equals(databaseMessage.getDatum())) {
                            matchesDatabaseMessage = true;
                            break;
                        }
                    }

                    if (!matchesDatabaseMessage) {
                        finalAdminMessages.add(adminMessage);
                    }
                }


                for (Message message : finalAdminMessages) {
                    for (Mitarbeiter admin : admins) {
                        message.setMitarbeiter(admin);
                        session.save(message);
                    }
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

    public static void saveAllMessages(List<Message> mitarbeiterMessages, List<Message>  messages) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();


                // Wegen ConcurrentModificationException
                Iterator<Message> iterator = mitarbeiterMessages.iterator();
                while (iterator.hasNext()) {
                    Message message = iterator.next();
                    for (Message message1 : messages) {
                        if (message.getMitarbeiter().getPersonalNummer() == message1.getMitarbeiter().getPersonalNummer() && message.getMessage().equals(message1.getMessage()) && message.getDatum().equals(message1.getDatum())) {
                            iterator.remove();  // Verwende die remove-Methode des Iterators
                            break;
                        }
                    }
                }

                for (Message message : mitarbeiterMessages) {
                    session.persist(message);
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

    public static List<Message> fetchAllMessages() {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            return session.createQuery("from Message", Message.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Message> fetchLatestMessagesForUser(Mitarbeiter user) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            return session.createQuery("from Message where mitarbeiter.personalNummer = :personalNummer order by datum asc", Message.class)
                    .setParameter("personalNummer", user.getPersonalNummer())
                    .setMaxResults(3)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Message> fetchAllMessagesForUser(Mitarbeiter user) {
        try (Session session = ServerService.getSessionFactory().openSession()) {
            Query<Message> query = session.createQuery("from Message where mitarbeiter.personalNummer = :personalNummer order by datum desc", Message.class);
            query.setParameter("personalNummer", user.getPersonalNummer());
            return query.list();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void makeMassagesSeen(Mitarbeiter user) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();

            String sql = "UPDATE Message SET seen = true WHERE Mitarbeiter_personalNummer = :personalNummer";

            session.createNativeQuery(sql)
                    .setParameter("personalNummer", user.getPersonalNummer())
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
