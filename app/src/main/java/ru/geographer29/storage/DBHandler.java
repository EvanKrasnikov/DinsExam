package ru.geographer29.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Properties;

import static ru.geographer29.settings.AppSettings.*;

public class DBHandler implements AutoCloseable{
    private final static Logger logger = LogManager.getLogger(DBHandler.class);
    private Connection connection;

    public DBHandler() {
        Properties props = System.getProperties();
        String host = props.getProperty(DATABASE_HOST);
        String port = props.getProperty(DATABASE_PORT);
        String user = props.getProperty(DATABASE_USER);
        String password = props.getProperty(DATABASE_PASS);
        String url = "jdbc:postgresql://" + host + ":" + port + "/";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Unable to connect to database");
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    public String selectElement(String query){
        String str = "";
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
