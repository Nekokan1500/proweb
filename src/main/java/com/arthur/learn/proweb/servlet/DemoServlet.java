package com.arthur.learn.proweb.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DemoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        System.out.println("Session ID is " + session.getId());
    }

}
