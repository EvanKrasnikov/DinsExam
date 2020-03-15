package ru.geographer29.settings;

import java.util.Properties;

public class AppSettings {

    public static final String DATABASE_HOST = "org.geographer29.database_host";
    public static final String DATABASE_PORT = "org.geographer29.database_port";
    public static final String DATABASE_USER = "org.geographer29.database_user";
    public static final String DATABASE_PASS = "org.geographer29.database_password";
    public static final String KAFKA_SERVER = "org.geographer29.kafka_server";
    public static final String KAFKA_DEFAULT_TOPIC = "org.geographer29.kafka_default_topic";

    static void init(){
        Properties props = new Properties();
        props.putIfAbsent(DATABASE_HOST, "localhost");
        props.putIfAbsent(DATABASE_PORT, "5432");
        props.putIfAbsent(DATABASE_USER, "postgres");
        props.putIfAbsent(DATABASE_PASS, "password");
        props.putIfAbsent(KAFKA_SERVER, "kafka_server:9091");
        props.putIfAbsent(KAFKA_DEFAULT_TOPIC, "alert");

        System.setProperties(props);
    }

}
