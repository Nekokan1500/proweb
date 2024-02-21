package com.arthur.learn.proweb.dao.base;

import com.arthur.learn.proweb.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseDao<T> {
    private QueryRunner runner = new QueryRunner();

    public List<T> getBeanList(String sql, Class<T> entityClass, Object ... parameters) {
        try {
            Connection connection = JDBCUtils.getConnection();
            return runner.query(connection, sql, new BeanListHandler<>(entityClass), parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public T getSingleBean(String sql, Class<T> entityClass, Object ... parameters) {
        try {
            Connection connection = JDBCUtils.getConnection();
            return runner.query(connection, sql, new BeanHandler<>(entityClass), parameters);
        }catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public long getCount(String sql){
        ScalarHandler<Long> scalarHandler = new ScalarHandler<>();
        try{
            Connection connection = JDBCUtils.getConnection();
            long count = runner.query(connection, sql, scalarHandler);
            return count;
        }catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // General-purpose method for insert, delete and update
    public int update(String sql, Object ... parameters){
        try{
            Connection connection = JDBCUtils.getConnection();
            int affectedRows = runner.update(connection, sql, parameters);
            return affectedRows;
        }catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
