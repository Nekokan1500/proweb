package com.arthur.learn.proweb.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DemoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(); // create a new session if no existing one
        // HttpSession session = request.getSession(true); -> same as getSession()
        // HttpSession session = request.getSession(false); -> returns null if no existing session
        System.out.println("Session ID is " + session.getId());
        System.out.println(session.isNew());
        System.out.println(session.getMaxInactiveInterval()); // 1800 seconds by default
        // session.setMaxInactiveInterval(0);
        // session.invalidate(); -> invalidates current session
        // session.getLastAccessedTime();
    }

}
