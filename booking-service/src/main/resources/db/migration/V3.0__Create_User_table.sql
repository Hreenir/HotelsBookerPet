create table users (
    id bigserial primary key,
    username varchar(255),
    password varchar(255),
    enabled boolean
);
