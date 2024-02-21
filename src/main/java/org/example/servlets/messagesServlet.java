package org.example.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.database.MessageDAO;
import org.example.entities.Mitarbeiter;

import java.io.IOException;

@WebServlet(name = "messagesServlet", value = "/messages")
public class messagesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Mitarbeiter user = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");
        session.setAttribute("messageCount", "");
        MessageDAO.makeMassagesSeen(user);


        request.getRequestDispatcher("WEB-INF/messages.jsp").forward(request, response);
    }


    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Mitarbeiter user = (Mitarbeiter) session.getAttribute("SessionMitarbeiter");
        session.setAttribute("messageCount", "");
        MessageDAO.makeMassagesSeen(user);
    }


}