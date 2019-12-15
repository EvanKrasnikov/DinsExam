package ru.geographer29.storage;

import java.sql.*;

public class DBHandler {
    private Connection connection;

    public void connect(String host, int port){
        String url = "jdbc:postgresql://" + host + ":" + port + "/traffic_limits";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Unable to close connection");
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
            System.out.println("Unable to select element");
            e.printStackTrace();
        }
        return str;
    }

    public void updateElement(String query){
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Unable to update element");
            e.printStackTrace();
        }
    }

}
