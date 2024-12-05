package com.mycompany.app.repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection;

    static {
        Properties props = new Properties();
        try (var input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            props.load(input);
            connection = DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
