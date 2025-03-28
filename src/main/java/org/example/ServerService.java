package org.example;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerService implements ServletContextListener {

    private static SessionFactory sessionFactory;
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        //  SessionFactory nur einmal erstellen
        // Erstelle die sessionFactory aus hibernate.cfg.xml
        sessionFactory = new Configuration().configure().buildSessionFactory();

        //  MessageCreater alle 30 Sekunden ausführen
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new MitarbeiterRefresh(), 0, 30, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new MessageCreater(), 0, 1, TimeUnit.MINUTES);



    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();


        //  SessionFactory schließen
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}