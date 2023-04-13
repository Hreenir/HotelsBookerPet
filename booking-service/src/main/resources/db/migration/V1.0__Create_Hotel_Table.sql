create sequence hotel_seq start with 1 increment by 50;

create table hotel (
    id bigint not null,
    address varchar(255),
    city varchar(255),
    country varchar(255),
    name varchar(255),
    rating float(53) not null,
    primary key (id)
);
