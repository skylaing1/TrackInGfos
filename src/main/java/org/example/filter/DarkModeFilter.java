package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName="DarkModeFilter",value="/*")
public class DarkModeFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Cookie[] cookies = httpRequest.getCookies();
        String darkMode = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("darkMode".equals(cookie.getName())) {
                    darkMode = cookie.getValue();
                }
            }
        }


        if (darkMode == null) {
            darkMode = "true";
            Cookie darkModeCookie = new Cookie("darkMode", darkMode);
            darkModeCookie.setMaxAge(Integer.MAX_VALUE);
            darkModeCookie.setPath("/");
            ((HttpServletResponse) response).addCookie(darkModeCookie);
        }

        request.setAttribute("darkMode", darkMode);
        chain.doFilter(request, response);

    }




}