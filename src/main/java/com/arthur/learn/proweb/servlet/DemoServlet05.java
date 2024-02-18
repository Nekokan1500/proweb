package com.arthur.learn.proweb.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoServlet05 extends HttpServlet {
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DemoServlet05");
        //String uname = (String)request.getAttribute("uname");
        ServletContext app = request.getServletContext();
        String uname = (String)app.getAttribute("uname");
        System.out.println(uname);
    }
}
