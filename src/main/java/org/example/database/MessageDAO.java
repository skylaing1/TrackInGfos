package org.example.database;

import org.example.entities.Message;
import org.example.entities.Mitarbeiter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageDAO {
    public static void deleteAllExpiredMessages() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();




            String sql = "DELETE FROM Message WHERE Datum < DATE_SUB(NOW(), INTERVAL 6 DAY)";

            session.createNativeQuery(sql).executeUpdate();

            session.getTransaction().commit();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void saveAllMessagesToAdmins(List<Message> adminMessages, List<Message> messages) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();

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
                        System.out.println("AdminMessage removed");
                        break;
                    }
                }

                if (!matchesDatabaseMessage) {
                    finalAdminMessages.add(adminMessage);
                    System.out.println("AdminMessage added");
                }
            }


            for (Message message : finalAdminMessages) {
                for (Mitarbeiter admin : admins) {
                    message.setMitarbeiter(admin);
                    session.save(message);
                }
            }

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveAllMessages(List<Message> mitarbeiterMessages, List<Message>  messages) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();

            // Wegen ConcurrentModificationException
            Iterator<Message> iterator = mitarbeiterMessages.iterator();
            while (iterator.hasNext()) {
                Message message = iterator.next();
                for (Message message1 : messages) {
                    if (message.getMitarbeiter().getPersonalNummer() == message1.getMitarbeiter().getPersonalNummer() && message.getMessage().equals(message1.getMessage()) && message.getDatum().equals(message1.getDatum())) {
                        iterator.remove();  // Verwende die remove-Methode des Iterators
                        System.out.println("MitarbeiterMessage removed");
                        break;
                    }
                }
            }

            for (Message message : mitarbeiterMessages) {
                session.save(message);
            }

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Message> fetchAllMessages() {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();

            List<Message> messages = session.createQuery("from Message", Message.class).list();

            session.getTransaction().commit();
            session.close();

            return messages;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Message> fetchAllMessagesForUser(Mitarbeiter user) {
        try (SessionFactory factory = new Configuration().configure().buildSessionFactory()) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();

            List<Message> messages = session.createQuery("from Message where mitarbeiter.personalNummer = :personalNummer order by datum asc", Message.class)
                    .setParameter("personalNummer", user.getPersonalNummer())
                    .list();


            session.getTransaction().commit();
            session.close();

            return messages;
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
