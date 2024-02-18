package com.arthur.learn.proweb.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoServlet4 extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("DemoServlet04");
        //request.setAttribute("uname", "Arthur");
        //request.getRequestDispatcher("demo5").forward(request, response); 
        //response.sendRedirect("demo5");
        ServletContext app = request.getServletContext();
        app.setAttribute("uname", "Arthur");
    }
    
}
