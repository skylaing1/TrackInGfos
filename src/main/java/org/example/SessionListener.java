package org.example;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SessionListener implements HttpSessionListener {

    // Thread-safe liste von aktiven Sessions
    private static final List<HttpSession> activeSessions = new CopyOnWriteArrayList<>();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // füge session zur liste hinzu
        activeSessions.add(event.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        // entferne session aus der liste
        activeSessions.remove(event.getSession());
    }

    // getter für die liste
    public static List<HttpSession> getActiveSessions() {
        return activeSessions;
    }
}