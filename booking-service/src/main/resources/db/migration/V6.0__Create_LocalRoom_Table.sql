
create table local_rooms(
    id bigserial primary key,
    room_number bigint,
    enabled boolean,
    room bigint not null,
    foreign key (room) references room (id)
);

