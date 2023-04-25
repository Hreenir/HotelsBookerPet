create sequence local_rooms_seq start with 1 increment by 50;

create table local_rooms(
    id bigint not null,
    room_number bigint,
    enabled boolean,
    room bigint not null,
    foreign key (room) references room (id),
    primary key (id)
);

