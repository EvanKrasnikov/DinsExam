package ru.geographer29.storage;

public enum Query {

    SELECT_MIN_TRAFFIC_LIMIT("SELECT traffic_limits.limit_value WHERE traffic_limits.limit_name = min"),
    SELECT_MAX_TRAFFIC_LIMIT("SELECT traffic_limits.limit_value WHERE traffic_limits.limit_name = max"),
    UPDATE_MIN_TRAFFIC_LIMIT("UPDATE traffic_limits.limit_value WHERE traffic_limits.limit_name = min"),
    UPDATE_MAX_TRAFFIC_LIMIT("UPDATE traffic_limits.limit_value WHERE traffic_limits.limit_name = max");

    private String query;

    Query(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
