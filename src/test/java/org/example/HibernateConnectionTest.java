package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnectionTest {
    public static void main(String[] args) {
        try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory()) {
            try (Session session = factory.openSession()) {
                System.out.println("Hibernate-Verbindung zur Datenbank erfolgreich hergestellt!");
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Verbindungsaufbau mit Hibernate:");
            e.printStackTrace();
        }
    }
}
