package com.arthur.learn.proweb.filter;

import com.arthur.learn.proweb.util.JDBCUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TransactionFilter implements Filter {

    private static Set<String> staticResourceExtNameSet;

    static {
        staticResourceExtNameSet = new HashSet<>();
        staticResourceExtNameSet.add(".png");
        staticResourceExtNameSet.add(".jpg");
        staticResourceExtNameSet.add(".css");
        staticResourceExtNameSet.add(".js");

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();
        String extName = servletPath.substring(servletPath.lastIndexOf("."));

        if (staticResourceExtNameSet.contains(extName)){
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        Connection connection = null;
        try{
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            chain.doFilter(servletRequest, servletResponse);
            connection.commit();
        }catch(Exception e){
            try{
                connection.rollback();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
            // page display: send caught exception to dedicated page for display
            String message = e.getMessage();
            servletRequest.setAttribute("systemMessage", message);
            servletRequest.getRequestDispatcher("/").forward(servletRequest, servletResponse);
        }finally{
            JDBCUtils.releaseConnection(connection);
        }

    }

    @Override
    public void destroy() {}
}
