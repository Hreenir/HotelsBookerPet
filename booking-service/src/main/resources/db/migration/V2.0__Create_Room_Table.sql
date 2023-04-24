create table room (
    id bigint not null,
    name varchar(255),
    capacity int,
    priceByDay numeric(20,2),
    hotel bigint not null,
    primary key (id),
    foreign key (hotel) references hotel (id)

);