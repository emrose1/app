package com.studio.bookings.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.studio.bookings.dao.UserDao;
import com.studio.bookings.entity.User;
import com.studio.bookings.entity.UserSession;
import com.studio.bookings.util.LoadDummyData;


public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }

    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	//Dummy Data Setup
    	UserDao userDao = new UserDao();
    	if (userDao.findAll().size() == 0) {
    		LoadDummyData ldd = new LoadDummyData();
    		ldd.initSetup();
    	}
    	
    	
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //request.setAttribute("message", username);
        
        if(username != null && password != null) {
            try {
                User user = userDao.getByUsernamePassword(username, password);

                if(user != null) {
                    UserSession userSession = new UserSession();
                    userSession.setUser(user);
                    userSession.setLoginTime(new Date());
                    request.getSession(true).setAttribute("userSession", userSession);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "Invalid username or password");
                    request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                }
            } catch (Exception e) {
            	request.setAttribute("message", e);
            	request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
                //e.printStackTrace();
            }
        } else {
            request.setAttribute("message", "Insert username and password!");
            request.getRequestDispatcher("/loginForm.jsp").forward(request, response);
        }

    }
}
