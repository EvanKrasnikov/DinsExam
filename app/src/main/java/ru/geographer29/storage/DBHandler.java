package ru.geographer29.storage;

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DBHandler {
    private final static Logger logger = LogManager.getLogger(DBHandler.class);
    private Connection connection;

    static {
        BasicConfigurator.configure();
    }

    public void connect(String host, int port){
        String url = "jdbc:postgresql://" + host + ":" + port + "/traffic_limits";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
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
            str = resultSet.getString(1);
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
