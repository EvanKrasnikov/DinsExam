FROM postgres:alpine

COPY setup-table.sql ./

USER postgres

ENTRYPOINT psql -f /setup-table.sql