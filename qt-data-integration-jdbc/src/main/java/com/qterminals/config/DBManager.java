package com.qterminals.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBManager extends SqlUtils {
    private static DBManager INSTANCE = new DBManager();
    private static Connection CONNECTION;
    private static Properties PROPERTIES;
    private DBManager() {
    }

    public static DBManager getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new DBManager();
        }

        InputStream inputStream = DBManager.class.getResourceAsStream("/db.properties");
        PROPERTIES = new Properties();

        try {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return INSTANCE;
    }

    public Connection getConnection(){
        if(CONNECTION == null){
            try {
                Class.forName(PROPERTIES.getProperty("jdbc.driver"));
                Properties clientProperties = new Properties();
                clientProperties.put("user", PROPERTIES.getProperty("jdbc.username"));
                clientProperties.put("password", PROPERTIES.getProperty("jdbc.password"));

                CONNECTION = DriverManager.getConnection(PROPERTIES.getProperty("jdbc.url"), clientProperties);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return CONNECTION;
    }

    public void closeConnection(){
        try {
            if(CONNECTION != null){
                CONNECTION.close();
                CONNECTION = null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
