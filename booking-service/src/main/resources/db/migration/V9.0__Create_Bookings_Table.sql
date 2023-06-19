create table bookings (
    id bigserial primary key,
    tg_user_id bigint references tgusers(id),
    local_room_id bigint references local_rooms(id),
    price_by_day numeric(20, 2),
    check_in_date datetime,
    check_out_date datetime
);