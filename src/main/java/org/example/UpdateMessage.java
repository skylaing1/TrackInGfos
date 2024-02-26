package org.example;

import jakarta.servlet.http.HttpSession;
import org.example.database.MessageTransaction;
import org.example.entities.Message;
import org.example.entities.Mitarbeiter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UpdateMessage {

    public static void RefreshMessage() {
        // Erhalte alle aktiven Sessions
        List<HttpSession> activeSessions = SessionListener.getActiveSessions();

        String messageCountStr = "";
        int messageCount = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        for (HttpSession session : activeSessions) {
            Mitarbeiter user = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");

            if (user != null) {
                List<Message> messages = MessageTransaction.fetchLatestMessagesForUser(user);

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

                        if (!message.getSeen()) {
                           messageCount++;
                        }
                        message.setMessageDateFormatted(message.getDatum().format(formatter));
                    }

                    if (messageCount > 3) {
                        messageCountStr = messageCount + "+";
                    } else if (messageCount > 0){
                        messageCountStr = String.valueOf(messageCount);
                    } else {
                        messageCountStr = "";
                    }

                    session.setAttribute("messageCount" , messageCountStr);
                    session.setAttribute("messages", messages);
                }
            }
        }

    }
}