package com.arthur.learn.proweb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/demo202")
public class DemoServlet202 extends HttpServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        System.out.println("Demo 202 service......");
        request.getRequestDispatcher("success.html").forward(request, response);;
    }
}
