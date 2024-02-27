package com.arthur.learn.proweb.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/demo202")
public class DemoFilter03 implements Filter{

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 03 C");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Filter 03 C2");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
