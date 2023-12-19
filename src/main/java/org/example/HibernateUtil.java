package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Die Konfiguration aus hibernate.cfg.xml laden
            Configuration configuration = new Configuration().configure();

            // SessionFactory erstellen
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Fehler beim Initialisieren der SessionFactory
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Beim Beenden der Anwendung die SessionFactory schließen
        getSessionFactory().close();
    }
}