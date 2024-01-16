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
        String darkMode = getDarkModeFromCookies(cookies);


        if (darkMode == null) {
            darkMode = "true";
            createDarkModeCookie(httpResponse, darkMode);
        }


        request.setAttribute("darkMode", darkMode);


        chain.doFilter(request, response);


    }


    private String getDarkModeFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("darkMode".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Helper method to create the darkMode cookie
    private void createDarkModeCookie(HttpServletResponse response, String darkModeValue) {
        Cookie darkModeCookie = new Cookie("darkMode", darkModeValue);
        darkModeCookie.setMaxAge(Integer.MAX_VALUE);
        darkModeCookie.setPath("/");
        response.addCookie(darkModeCookie);
    }

}