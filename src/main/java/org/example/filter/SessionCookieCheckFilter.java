package org.example.filter;

import org.example.database.TokenDAO;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.entities.Token;

import java.io.IOException;

@WebFilter(filterName="SessionCookieCheckFilter",value="/*") // Filter für alle URLs
public class SessionCookieCheckFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();
        boolean isStaticResource = ((HttpServletRequest) request).getRequestURI().startsWith("/resources/");

        // Überprüfen, ob es sich um die Login- oder Registrierungsseite handelt

        if (requestURI.contains("login") || requestURI.contains("register") || isStaticResource ) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);

        // Überprüfen, ob eine gültige Sitzung vorhanden ist
        if (session == null || session.getAttribute("SessionMitarbeiter") == null) {
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("rememberMe")) {
                        if (TokenDAO.checkToken(cookie.getValue())) {
                            session = httpRequest.getSession();
                            session.setAttribute("SessionMitarbeiter", TokenDAO.getMitarbeiterByToken(cookie.getValue()));
                            System.out.println("RememberMe cookie angemeldet"); //Test
                            chain.doFilter(request, response);
                        } else {
                            cookie.setMaxAge(0);
                            httpResponse.sendRedirect("/login");
                        }
                        return;
                    }
                }
            }
            httpResponse.sendRedirect("/login");
        } else {
            if (requestURI.equals("/")) {
                httpResponse.sendRedirect("/dashboard");
                System.out.println("Session war noch da"); //Test
                return;
            }
            chain.doFilter(request, response);

        }
    }

}