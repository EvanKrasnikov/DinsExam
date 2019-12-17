CREATE SCHEMA traffic_limits;
CREATE TABLE traffic_limits.limits_per_hour (limit_name VARCHAR(64), limit_value INTEGER, effective_date DATE);
INSERT INTO traffic_limits.limits_per_hour(limit_name, limit_value, effective_date) VALUES ('min', 1024, '2019-12-10');
INSERT INTO traffic_limits.limits_per_hour(limit_name, limit_value, effective_date) VALUES ('max', 1073741824, '2019-12-10');
