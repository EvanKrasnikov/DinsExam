package ru.geographer29.storage;

public class Queries {

    public final static String SELECT_MIN_TRAFFIC_LIMIT = "SELECT limit_value FROM traffic_limits.limits_per_hour WHERE limit_name = 'min'";
    public final static String SELECT_MAX_TRAFFIC_LIMIT = "SELECT limit_value FROM traffic_limits.limits_per_hour WHERE limit_name = 'max'";
    public final static String UPDATE_MIN_TRAFFIC_LIMIT = "UPDATE limit_value FROM traffic_limits.limits_per_hour WHERE limit_name = 'min'";
    public final static String UPDATE_MAX_TRAFFIC_LIMIT = "UPDATE limit_value FROM traffic_limits.limits_per_hour WHERE limit_name = 'max'";

}
