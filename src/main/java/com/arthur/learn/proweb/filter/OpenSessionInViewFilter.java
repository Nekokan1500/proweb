package com.arthur.learn.proweb.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.arthur.learn.proweb.transaction.TransactionManager;


@WebFilter("/fruit.do")
public class OpenSessionInViewFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            TransactionManager.beginTransaction();
            System.out.println("=====Beinning transaction=====");
            chain.doFilter(request, response);
            TransactionManager.commit();
            System.out.println("=====Transaction committed=====");
        } catch (Exception e){
            e.printStackTrace();
            try {
                TransactionManager.rollback();
                System.out.println("!!!!!Transaction rolled back!!!!!");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {
    }
}
