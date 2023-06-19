create table tgusers (
    id bigserial UNIQUE primary key,
    role bigint,
    foreign key (role) references roles (id)
);