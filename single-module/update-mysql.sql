USE eventuate;

create table cdc_monitoring (reader_id VARCHAR(1000) PRIMARY KEY, last_time BIGINT);