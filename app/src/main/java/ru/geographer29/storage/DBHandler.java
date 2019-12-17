package ru.geographer29.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Properties;

public class DBHandler {
    private final static Logger logger = LogManager.getLogger(DBHandler.class);
    private Connection connection;

    public void connect(String host, int port){
        String url = "jdbc:postgresql://" + host + ":" + port + "/";
        Properties props = new Properties();
        props.putIfAbsent("user", "postgres");
        props.putIfAbsent("password", "password");

        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            logger.error("Unable to connect to database");
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Unable to close connection");
            e.printStackTrace();
        }
    }

    public String selectElement(String query){
        String str = null;
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ){
            while (resultSet.next()){
                str = resultSet.getString(1);
            }
        } catch (SQLException e) {
            logger.error("Unable to select element");
            e.printStackTrace();
        }
        return str;
    }

    public void updateElement(String query){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.error("Unable to update element");
            e.printStackTrace();
        }
    }

}
