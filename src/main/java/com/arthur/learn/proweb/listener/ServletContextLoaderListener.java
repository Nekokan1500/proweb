package com.arthur.learn.proweb.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.arthur.learn.proweb.io.BeanFactory;
import com.arthur.learn.proweb.io.ClasspathXmlApplicationContext;

@WebListener
public class ServletContextLoaderListener implements ServletContextListener{

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();
        String path = application.getInitParameter("contextConfigLocation");
        BeanFactory beanFactory = new ClasspathXmlApplicationContext(path);
        
        application.setAttribute("beanFactory", beanFactory);
    }
}
