package org.example.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.SessionListener;
import org.example.database.MessageDAO;
import org.example.entities.Message;
import org.example.entities.Mitarbeiter;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "messagesServlet", value = "/messages")
public class messagesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Mitarbeiter user = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");
        session.setAttribute("messageCount", "");
        List<Message> messages = MessageDAO.fetchAllMessagesForUser(user);
        MessageDAO.makeMassagesSeen(user);




        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");


        for (Message message : messages) {
            switch (message.getStatus()) {
                case "danger", "warning":
                    message.setIcon("fa-exclamation-triangle");
                    break;
                case "success":
                    message.setIcon("fa-check");
                    break;
            }

            message.setMessageDateFormatted(message.getDatum().format(formatter));
        }



        request.setAttribute("messagesList", messages);


        request.getRequestDispatcher("WEB-INF/messages.jsp").forward(request, response);
    }


    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Mitarbeiter user = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");
        session.setAttribute("messageCount", "");
        MessageDAO.makeMassagesSeen(user);
    }


}