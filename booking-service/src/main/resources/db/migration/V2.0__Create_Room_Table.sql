create table room (
    id bigserial primary key,
    name varchar(255),
    capacity int,
    priceByDay numeric(20,2),
    hotel bigint not null,
    foreign key (hotel) references hotel (id)
);