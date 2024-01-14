package org.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*") // Filter für alle URLs
public class SessionCookieCheckFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        // Überprüfen, ob es sich um die Login- oder Registrierungsseite handelt
        if (requestURI.contains("login") || requestURI.contains("register")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);

        // Überprüfen, ob eine gültige Sitzung vorhanden ist
        if (session == null || session.getAttribute("SessionMitarbeiter") == null) {

            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        } else {
            // Gültige Sitzung, lassen Sie die Anfrage durch den Filter
            chain.doFilter(request, response);

        }
    }

}