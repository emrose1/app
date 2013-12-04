package com.studio.bookings.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.studio.bookings.entity.UserSession;


public class SecurityFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        System.out.println("[SecurityFilter] ServletPath : " + request.getServletPath());
        if(!request.getServletPath().equalsIgnoreCase("/login") &&
                !request.getServletPath().equalsIgnoreCase("/logout") &&
                !request.getServletPath().equalsIgnoreCase("/loginForm.jsp") &&
                !request.getServletPath().equalsIgnoreCase("/css/format.css")) {
            System.out.println("[SecurityFilter] session : " + session);
            if(session != null) {
                UserSession userSession = (UserSession) session.getAttribute("userSession");

                if(userSession == null) {
                    System.out.println("[SecurityFilter] userSession : " + userSession);
                    //response.sendRedirect("/loginForm.jsp?message=Please login first!");
                    request.setAttribute("message", "Please login first!");
                    request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("message", "Please login!");
                request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
