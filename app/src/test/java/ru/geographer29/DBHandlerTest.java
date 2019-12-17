package ru.geographer29;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.*;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DBHandlerTest {
    private Connection connection;

    @Before
    public void init() throws SQLException{
        String host = "localhost";
        int port = 5432;
        String url = "jdbc:postgresql://" + host + ":" + port + "/";
        Properties props = new Properties();
        props.putIfAbsent("user", "postgres");
        props.putIfAbsent("password", "password");

        connection = DriverManager.getConnection(url, props);
    }

    @Test
    public void shouldConnect() {
        assertNotNull(connection);
    }

    @Test
    public void shouldSelectTrafficValue() throws SQLException{
        String query = "SELECT limit_value FROM traffic_limits.limits_per_hour WHERE limit_name = 'min'";
        int value = 0;

        try (
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);
                ) {

            while(rs.next()){
                value = rs.getInt(1);
            }

            assertEquals(1024, value);
        }
    }

    @Test
    public void shouldUpdateAndThenSelectTrafficValue() throws SQLException{
        String updateQuery = "UPDATE traffic_limits.limits_per_hour SET limit_value = 2048 WHERE limit_name = 'min'";
        String selectQuery = "SELECT limit_value FROM traffic_limits.limits_per_hour WHERE limit_name = 'min'";
        int value = 0;

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(updateQuery);

            ResultSet rs = statement.executeQuery(selectQuery);

            while(rs.next()){
                value = rs.getInt(1);
            }

            rs.close();

            assertEquals(2048, value);
        }
    }

    @After
    public void cleanUp() throws SQLException{
        String updateQuery = "UPDATE traffic_limits.limits_per_hour SET limit_value = 1024 WHERE limit_name = 'min'";

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(updateQuery);
        }

        connection.close();
    }

}
