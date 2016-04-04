create table IF NOT EXISTS todo (
  id varchar(255) PRIMARY KEY,
  title varchar(255),
  completed BOOLEAN,
  order_id INTEGER,
  deleted BOOLEAN
);