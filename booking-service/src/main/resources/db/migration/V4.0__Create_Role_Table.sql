create sequence roles_seq start with 1 increment by 50;

create table roles (
    id bigint not null,
    name varchar(255),
    primary key (id)
);
