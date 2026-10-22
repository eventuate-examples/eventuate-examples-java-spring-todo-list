ARG EVENTUATE_COMMON_DATABASE_VERSION
FROM eventuateio/eventuate-mysql8:$EVENTUATE_COMMON_DATABASE_VERSION
COPY schema-mysql.sql /docker-entrypoint-initdb.d
