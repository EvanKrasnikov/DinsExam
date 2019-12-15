package ru.geographer29.storage;

public enum Query {

    SELECT_MIN_TRAFFIC_LIMIT("SELECT limit_value WHERE limit_name = min"),
    SELECT_MAX_TRAFFIC_LIMIT("SELECT limit_value WHERE limit_name = max"),
    UPDATE_MIN_TRAFFIC_LIMIT("UPDATE limit_value WHERE limit_name = min"),
    UPDATE_MAX_TRAFFIC_LIMIT("UPDATE limit_value WHERE limit_name = max");

    private String query;

    Query(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
