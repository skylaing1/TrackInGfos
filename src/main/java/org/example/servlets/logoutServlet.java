package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.database.TokenDAO;

import java.io.IOException;

@WebServlet(name = "logoutServlet", value = "/logout")
public class logoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("rememberMe")) {
                TokenDAO.deleteTokenByContent(cookie.getValue());
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
            session.invalidate();
            response.sendRedirect("/login");

    }


}