package com.arthur.learn.proweb.transaction;

import java.sql.Connection;
import java.sql.SQLException;

import com.arthur.learn.proweb.util.JDBCUtils;

public class TransactionManager {
    
    public static void beginTransaction() throws SQLException{
        JDBCUtils.getConnection().setAutoCommit(false);
    }

    public static void commit() throws SQLException{
        Connection connection = JDBCUtils.getConnection();
        connection.commit();
        JDBCUtils.releaseConnection(connection);
    }

    public static void rollback() throws SQLException{
        Connection connection = JDBCUtils.getConnection();
        connection.rollback();
        JDBCUtils.releaseConnection(connection);
    }
}
