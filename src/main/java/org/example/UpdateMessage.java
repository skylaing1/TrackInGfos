package org.example;

import jakarta.servlet.http.HttpSession;
import org.example.database.MessageDAO;
import org.example.entities.Message;
import org.example.entities.Mitarbeiter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UpdateMessage {

    public static void RefreshMessage() {
        // Erhalte alle aktiven Sessions
        List<HttpSession> activeSessions = SessionListener.getActiveSessions();

        String messageCount = "1";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        for (HttpSession session : activeSessions) {
            Mitarbeiter user = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

            if (user != null) {
                List<Message> messages = MessageDAO.fetchAllMessagesForUser(user);

                if (messages != null) {

                    for (Message message : messages) {
                        switch (message.getStatus())    {
                            case "danger", "warning":
                                message.setIcon("fa-exclamation-triangle");
                                break;
                            case "success":
                                message.setIcon("fa-check");
                                break;
                        }

                        message.setMessageDateFormatted(message.getDatum().format(formatter));
                    }

                    if (messages.size() > 3) {
                        messageCount = String.valueOf(messages.size()) + "+";
                    } else {
                        messageCount = String.valueOf(messages.size());
                    }
                    session.setAttribute("messageCount" , messageCount);
                    session.setAttribute("messages", messages);
                }
            }
        }

    }
}