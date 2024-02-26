package com.arthur.learn.proweb.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/demo201a"},
            initParams = {
                @WebInitParam(name="message", value="Hello World"),
                @WebInitParam(name="uname", value="Arthur")
            })
public class Demo201Servlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        String initValue = config.getInitParameter("uname");
        System.out.println("Init parameter: " + initValue);

        ServletContext servletContext = getServletContext();
        String contextParam = servletContext.getInitParameter("contextConfigLocation");
        System.out.println("Context parameter: " + contextParam);

    }
    
}
