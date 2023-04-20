create sequence users_seq start with 1 increment by 50;

create table users (
    id bigint not null,
    username varchar(255),
    password varchar(255),
    enabled boolean,
    primary key (id)
);
