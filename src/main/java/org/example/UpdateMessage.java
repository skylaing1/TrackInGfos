package org.example;

import jakarta.servlet.http.HttpSession;
import org.example.database.MessageDAO;
import org.example.entities.Message;
import org.example.entities.Mitarbeiter;

import java.util.List;

public class UpdateMessage {

    public static void RefreshMessage() {
        // Erhalte alle aktiven Sessions
        List<HttpSession> activeSessions = SessionListener.getActiveSessions();

        for (HttpSession session : activeSessions) {
            Mitarbeiter user = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

            if (user != null) {
                List<Message> messages = MessageDAO.fetchAllMessagesForUser(user);

                if (messages != null) {
                    session.setAttribute("messages", messages);
                    System.out.println("MessageRefreshFilter");
                    for (Message message : messages) {
                        System.out.println(message.getMessage());
                    }
                }
            }
        }

    }
}