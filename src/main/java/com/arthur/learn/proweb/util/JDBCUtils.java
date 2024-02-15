package com.arthur.learn.proweb.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/*
    1. Get database connection from data source
    2. Bind database connection with ThreadLocal
    3. Release the binding when releasing the thread
 */
public class JDBCUtils {

    private static DataSource dataSource;

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    // Initialize data source in a static code block
    static {
        // read database connection parameters from jdbc.resources
        // Use a relative path to the classpath. Contents of the resources directory will be placed under
        // WEB-INF/classes folder, which is the classpath
        try {
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            InputStream stream = classLoader.getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(stream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        Connection connection = threadLocal.get();
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return connection;
    }

    public static void releaseConnection(Connection connection){
        if (connection != null){
            try{
                connection.close();
                threadLocal.remove();
            }catch(SQLException e){
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}
