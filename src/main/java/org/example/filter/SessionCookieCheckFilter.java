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

@WebFilter("/*")
public class SessionCookieCheckFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Überprüfen, ob es sich um die Login- oder Registrierungsseite handelt
        HttpSession session = httpRequest.getSession(false);

        if (requestURI.endsWith("/index.jsp") || requestURI.endsWith("/register.jsp")) {

            // Überprüfen, ob bereits ein Mitarbeiter in der Session vorhanden ist
            if (session != null && session.getAttribute("SessionMitarbeiter") != null) {
                // Mitarbeiter bereits angemeldet, nichts tun
                chain.doFilter(request, response);
            } else {
                // Weiter zu Login oder Register, da kein Mitarbeiter in der Session vorhanden ist
                chain.doFilter(request, response);
            }
        } else {

            if (session == null || session.getAttribute("SessionMitarbeiter") == null) {
                // Kein Mitarbeiter in der Session, auf die Login-Seite umleiten
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
            } else {
                // Gültige Sitzung, lassen Sie die Anfrage durch den Filter
                chain.doFilter(request, response);
            }
        }



    }

    // Weitere Filter-Methoden (init, destroy) können implementiert werden
}
