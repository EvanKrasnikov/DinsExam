#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE SCHEMA traffic_limits;
    CREATE TABLE traffic_limits.limits_per_hour (limit_name VARCHAR(64), limit_value INTEGER, effective_date DATE);
    INSERT INTO traffic_limits.limits_per_hour(limit_name, limit_value, effective_date) 
        VALUES ('min', 1024, '2019-12-10'), ('max', 1073741824, '2019-12-10');
EOSQL