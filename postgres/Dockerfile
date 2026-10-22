ARG EVENTUATE_COMMON_DATABASE_VERSION
FROM eventuateio/eventuate-postgres:$EVENTUATE_COMMON_DATABASE_VERSION
COPY schema-postgres.sql /docker-entrypoint-initdb.d
